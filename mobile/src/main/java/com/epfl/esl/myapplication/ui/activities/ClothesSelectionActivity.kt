package com.epfl.esl.myapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.ui.adapters.MyOutfitAdapter

import com.epfl.esl.myapplication.utils.Constants
import com.myshoppal.ui.adapters.MyItemsListAdapter
import kotlinx.android.synthetic.main.activity_clothes_selection.*


class ClothesSelectionActivity : AppCompatActivity(){

    private lateinit var selected_category: String
    private lateinit var messenger_top_id: String
    private lateinit var messenger_bottom_id: String
    private lateinit var messenger_shoes_id: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes_selection)


        if (intent.hasExtra(Constants.CATEGORY)) {
            selected_category = intent.getStringExtra(Constants.CATEGORY)!!
        }

        if(intent.hasExtra(Constants.OUTFIT_TOP)){
            messenger_top_id = intent.getStringExtra(Constants.OUTFIT_TOP)!!
        }
        if(intent.hasExtra(Constants.BOTTOM)){
            messenger_bottom_id = intent.getStringExtra(Constants.OUTFIT_BOTTOM)!!
        }
        if(intent.hasExtra(Constants.OUTFIT_SHOES)){
            messenger_shoes_id = intent.getStringExtra(Constants.OUTFIT_SHOES)!!
        }



    }

    fun successItemsListFromFireStoreClothes(clothing: ArrayList<Clothing>) {

        // Hide Progress dialog.
//        hideProgressDialog()

        if (clothing.size > 0) {
            rv_selection_clothes.visibility = View.VISIBLE // which contains the recyclerview

            rv_selection_clothes.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)

            rv_selection_clothes.setHasFixedSize(false)


            val adapteroutfit = MyOutfitAdapter(this, clothing, messenger_top_id, messenger_bottom_id,messenger_shoes_id)


            rv_selection_clothes.adapter = adapteroutfit



        } else {
            rv_selection_clothes.visibility = View.GONE

        }
    }

    private fun getItemListFromFireStore() {
        // Show the progress dialog.
//        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getItemsListClothes(this,selected_category)
    }

    override fun onResume() {
        super.onResume()
        getItemListFromFireStore()
    }




}