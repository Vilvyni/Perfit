package com.epfl.esl.myapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.models.Outfit
import kotlinx.android.synthetic.main.outfit_list_layout.view.*
import com.epfl.esl.myapplication.utils.GlideLoader

open class MyOutfitAdapter (
    private val context: Context,
    private var list: ArrayList<Outfit>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.outfit_list_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            GlideLoader(context).loadItemPicture(model.uri_top, holder.itemView.iv_outfit_top_image)
            GlideLoader(context).loadItemPicture(model.uri_trouser, holder.itemView.iv_outfit_bottom_image)
            GlideLoader(context).loadItemPicture(model.uri_shoes, holder.itemView.iv_outfit_shoes_image)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}