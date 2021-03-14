package com.mertkavrayici.marvelkarakterlerideneme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertkavrayici.marvelkarakterlerideneme.Adapters.HeroesAdapter
import com.mertkavrayici.marvelkarakterlerideneme.Models.Hero
import com.mertkavrayici.marvelkarakterlerideneme.Utils.CHARACTER_LIMIT

import com.mertkavrayici.marvelkarakterlerideneme.Utils.hasInternet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: HeroesViewModel
    var offset = 0
    private var loading = false
    var pastVisiblesItems = 0
    var totalItemCount: Int = 0
    var mList: MutableList<Hero> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(HeroesViewModel::class.java)

        setupRecyclerView()
//Modelden data aldıkça yüklüyor
        viewModel.herosLiveData.observe(this, Observer {
            it?.let { heros ->
                mList.addAll(heros)
                hero_recyclerView.adapter!!.notifyDataSetChanged()
                loading = false
            }
        })
        //Bağlantı olduğu sürece çalışıyor
        checkConnection()
    }

    fun setupRecyclerView() {

        with(hero_recyclerView) {

            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)

            //Constants'da 30 olarak limit belirlendi

            //Scrool kaydırıldıkça 30 lu olarak listeleme yapıyor ve her 30 yüklemenin ardından bağlantı kontrolü yapılıyor.

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView, dx: Int, dy: Int
                ) {
                    if (dy > 0) {
                        totalItemCount = layoutManager!!.itemCount
                        pastVisiblesItems =
                            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                        if (!loading) {
                            if (pastVisiblesItems >= totalItemCount - 1) {

                                loading = true
                                offset += CHARACTER_LIMIT
                                checkConnection()
                            }
                        }
                    }
                }
            })

            adapter = HeroesAdapter(this@MainActivity, mList) { hero ->
                val intent = DetailsActivity.getStartIntent(this@MainActivity, hero)
                this@MainActivity.startActivity(intent)
            }
        }
    }

    fun checkConnection() {
        if (hasInternet(this)) {

            viewModel.getHeros(offset)

        }
    }

}
