package com.epfl.esl.myapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.ui.fragments.ClosetFragment
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class MyItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Item>,
    private val fragment: ClosetFragment
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
                R.layout.item_list_layout,
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
        if (holder is MyViewHolder) {

            GlideLoader(context).loadItemPicture(model.image, holder.itemView.iv_item_image)

            holder.itemView.tv_item_name.text = model.title
            holder.itemView.tv_item_price.text = "$${model.price}"

//            //  Assigning the click event to the delete button.
//            // START
//            holder.itemView.ib_delete_product.setOnClickListener {
//
//                //Now let's call the delete function of the ProductsFragment.
//                // START
//                fragment.deleteItem(model.item_id)
//                // END
            }
            // END
        }

    override fun getItemCount(): Int {

        return list.size
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

