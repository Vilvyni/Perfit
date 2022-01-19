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
//
//    private lateinit var selected_category: String
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_clothes_selection)
//// create the action bar
//        setupActionBar()
//
//
//        if (intent.hasExtra(Constants.CATEGORY)) {
//            selected_category = intent.getStringExtra(Constants.CATEGORY)!!
//        }
//
//
//
//    }
//
//    fun successItemsListFromFireStoreClothes(clothing: ArrayList<Clothing>) {
//
//        // Hide Progress dialog.
////        hideProgressDialog()
//
//        if (clothing.size > 0) {
//            rv_selection_clothes.visibility = View.VISIBLE // which contains the recyclerview
//
//            rv_selection_clothes.layoutManager = LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL,false)
//
//            rv_selection_clothes.setHasFixedSize(false)
//
//
//            val adapteroutfit = MyOutfitAdapter(this,clothing )
//
//
//            rv_selection_clothes.adapter = adapteroutfit
//
//
//
//        } else {
//            rv_selection_clothes.visibility = View.GONE
//
//        }
//    }
//
//    private fun getItemListFromFireStore() {
//        // Show the progress dialog.
////        showProgressDialog(resources.getString(R.string.please_wait))
//
//        FirestoreClass().getItemsListClothes(this,selected_category)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        //  getItemListFromFireStore()
//    }
//
//    /**
//     * A function for actionBar Setup.
//     */
//    private fun setupActionBar() {
//
//        setSupportActionBar(toolbar_clothes_selection_activity)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
//        }
//
//        toolbar_clothes_selection_activity.setNavigationOnClickListener { onBackPressed() }
//    }




}