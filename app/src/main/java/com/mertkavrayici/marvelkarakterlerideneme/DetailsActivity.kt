package com.mertkavrayici.marvelkarakterlerideneme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertkavrayici.marvelkarakterlerideneme.Adapters.DetailAdapter
import com.mertkavrayici.marvelkarakterlerideneme.Models.Hero
import com.mertkavrayici.marvelkarakterlerideneme.Utils.getUrlImage
import com.mertkavrayici.marvelkarakterlerideneme.Utils.loadImageView
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val hero: Hero? = intent.getParcelableExtra(HERO) ?: return
        receiveHero(hero)
    }

    fun receiveHero(hero:Hero?){
        if(hero!=null){
            setData(hero)
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setData(hero:Hero){

        val urlImg = getUrlImage(hero.thumbnail?.path ?: "", hero.thumbnail?.extension ?: "","landscape_xlarge")

        try {
            loadImageView(hero_detail_image,urlImg)
        }catch (e:Exception){
            e.printStackTrace()
        }

        hero_detail_name.text = hero.name
        hero_detail_description.text = hero.description

        if ((hero.comics?.items ?: emptyList()).isNotEmpty()){
            with(comics_recyclerView){
                layoutManager = LinearLayoutManager(this@DetailsActivity, RecyclerView.VERTICAL,false)
                setHasFixedSize(true)
                adapter = DetailAdapter(this@DetailsActivity, hero.comics?.items!!)
            }
        }
    }

    companion object {
        private const val HERO = "HERO"

        fun getStartIntent(context: Context, hero: Hero): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(HERO, hero)
            }
        }
    }
}

