package com.myshoppal.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.ui.fragments.ClosetFragment
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class MyItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Clothing>,
    private val fragment: ClosetFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            GlideLoader(context).loadItemPicture(model.image, holder.itemView.iv_item_image)

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}