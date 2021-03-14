package com.mertkavrayici.marvelkarakterlerideneme.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mertkavrayici.marvelkarakterlerideneme.Models.Hero
import com.mertkavrayici.marvelkarakterlerideneme.R
import com.mertkavrayici.marvelkarakterlerideneme.Utils.getUrlImage
import com.mertkavrayici.marvelkarakterlerideneme.Utils.loadImageView
import kotlinx.android.synthetic.main.hero_item_row.view.*

class HeroesAdapter(context: Context,
                    list: List<Hero>,
                    val onItemClickListener:((hero:Hero) -> Unit)) : RecyclerView.Adapter<HeroesAdapter.MyViewHolder?>() {

    private var mContext =context
    private var mList = list
    private var mlayoutInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //
    private lateinit var mView: View
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mView = mlayoutInflater.inflate(R.layout.hero_item_row,parent,false)
        return MyViewHolder(mView,onItemClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindHero(mList[position])
        setAnimation(holder.itemView, position) //Ufak bir animasyon eklendi
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount() = mList.count()

    inner class MyViewHolder(itemView: View,
                             private val onItemClickListener: ((hero: Hero) -> Unit)) :RecyclerView.ViewHolder(itemView){

        private var heroImageView: ImageView = itemView.hero_image
        private var heroNameTextView: TextView = itemView.hero_name


        fun bindHero(hero: Hero) {
//Fotoğraf boyutu dökümandan seçildi.Değiştirilebilir.
            val urlImg = getUrlImage(hero.thumbnail?.path ?: "", hero.thumbnail?.extension ?: "", "portrait_medium")

            try {
                loadImageView(heroImageView,urlImg)
            }catch (e:Exception){
                e.stackTrace
            }
            heroNameTextView.text = hero.name


            itemView.setOnClickListener{
                onItemClickListener.invoke(hero)
            }
        }

    }
}