package com.mertkavrayici.marvelkarakterlerideneme.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mertkavrayici.marvelkarakterlerideneme.Models.ItemComics
import com.mertkavrayici.marvelkarakterlerideneme.R
import kotlinx.android.synthetic.main.hero_item_detail_row.view.*

class DetailAdapter(context: Context,list: List<ItemComics>) :RecyclerView.Adapter<DetailAdapter.ViewHolder?>() {

    private var mContext =context
    private var  limit=10
    private var mList = list
    private var mlayoutInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //
    private lateinit var mView: View



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mView = mlayoutInflater.inflate(R.layout.hero_item_detail_row,parent,false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindLine(mList[position])
    }

    override fun getItemCount() = mList.count()

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private var comicsTextView: TextView = itemView.comics_text

        fun bindLine(line: ItemComics) {

                comicsTextView.text = line.name
            }
        }
    }

