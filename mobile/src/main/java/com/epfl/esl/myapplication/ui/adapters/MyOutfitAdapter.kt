package com.epfl.esl.myapplication.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.models.Outfit
import kotlinx.android.synthetic.main.outfit_list_layout.view.*


import com.epfl.esl.myapplication.utils.GlideLoader



open class MyOutfitAdapter (
    private val context: Context,
    private var list: ArrayList<Outfit>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.outfit_list_layout,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        Log.d("MEMEME",model.uri_top)


        if (holder is MyViewHolder) {
            GlideLoader(context).loadItemPicture(model.uri_top, holder.itemView.iv_outfit_top_image)
            GlideLoader(context).loadItemPicture(model.uri_trouser, holder.itemView.iv_outfit_bottom_image)
            GlideLoader(context).loadItemPicture(model.uri_shoes, holder.itemView.iv_outfit_shoes_image)

        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}