package com.epfl.esl.myapplication.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_outfit.*
import kotlinx.android.synthetic.main.item_list_layout.view.*

class AddOutfitActivity :BaseActivity(), View.OnClickListener{
    private var empty_top: Boolean = false
    private var empty_bottom: Boolean = false
    private var empty_shoes: Boolean = false

    private var count_top: Int = 0
    private var count_bottom: Int = 0
    private var count_shoes: Int = 0

    private var index_top: Int = 0
    private var index_bottom: Int = 0
    private var index_shoes: Int = 0

    private lateinit var top_list : ArrayList<Clothing>
    private lateinit var bottom_list : ArrayList<Clothing>
    private lateinit var shoes_list : ArrayList<Clothing>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_outfit)

        setupActionBar()
        top_left.setOnClickListener(this)
        bottom_left.setOnClickListener(this)
        shoes_left.setOnClickListener(this)

        top_right.setOnClickListener(this)
        bottom_right.setOnClickListener(this)
        shoes_right.setOnClickListener(this)



    }

    private fun getItemListFromFireStore() {
        // Show the progress dialog.
//        showProgressDialog(resources.getString(R.string.please_wait))
        // Call the function of Firestore class.
        FirestoreClass().getItemsListClothes(this, Constants.TOP)
        FirestoreClass().getItemsListClothes(this,Constants.BOTTOM)
        FirestoreClass().getItemsListClothes(this,Constants.SHOES)
    }

    override fun onResume() {
        super.onResume()
        getItemListFromFireStore()
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.top_left -> {
                    index_top = index_top-1
                    //if it is equal to 0
                    if(index_top<0 ) {index_top=count_top-1}

                    var model = top_list[index_top]
                    GlideLoader(this).loadUserPicture(model.image,iv_add_outfit_top)
                    Log.e("loloo",index_top.toString())
                }
//
                R.id.bottom_left -> {
                    index_bottom = index_bottom-1
                    //if it is equal to 0
                    if(index_bottom<0 ) {index_bottom=count_bottom-1}

                    var model = bottom_list[index_bottom]
                    GlideLoader(this).loadUserPicture(model.image,iv_add_outfit_bottom)
                    Log.e("loloo",index_bottom.toString())

                }

                R.id.shoes_left -> {
                    index_shoes = index_shoes-1
                    //if it is equal to 0
                    if(index_shoes<0 ) {index_shoes=count_shoes-1}

                    var model = shoes_list[index_shoes]
                    GlideLoader(this).loadUserPicture(model.image,iv_add_outfit_shoes)
                    Log.e("loloo",index_shoes.toString())

                }
                
                R.id.top_right -> {
                    index_top = index_top+1
                    //if it is equal to 0
                    if(index_top==count_top ) {index_top=0}

                    var model = top_list[index_top]
                    GlideLoader(this).loadUserPicture(model.image,iv_add_outfit_top)
                    Log.e("loloo",index_top.toString())
                }
//
                R.id.bottom_right -> {
                    index_bottom = index_bottom+                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    1
                    //if it is equal to 0
                    if(index_bottom==count_bottom ) {index_bottom=0}

                    var model = bottom_list[index_bottom]
                    GlideLoader(this).loadUserPicture(model.image,iv_add_outfit_bottom)
                    Log.e("loloo",index_bottom.toString())

                }

                R.id.shoes_right -> {
                    index_shoes = index_shoes+1
                    //if it is equal to 0
                    if(index_shoes==count_shoes ) {index_shoes=0}

                    var model = shoes_list[index_shoes]
                    GlideLoader(this).loadUserPicture(model.image,iv_add_outfit_shoes)
                    Log.e("loloo",index_shoes.toString())

                }

                R.id.btn_outfit_add -> {

                }
            }
        }
    }

    fun getItemCount(list:ArrayList<Clothing>): Int {
        return list.size
    }

    fun successItemsListFromFireStoreClothes(clothing: ArrayList<Clothing>,category:String) {
        if(category == Constants.TOP){
            top_list= clothing
            count_top = getItemCount(clothing)
            Log.d("lolo", count_top.toString())
            if(count_top == 0 ){
                empty_top = true
            }else{
                empty_top = false
            }



        }else if(category == Constants.BOTTOM){
            bottom_list= clothing
            count_bottom = getItemCount(clothing)
            Log.d("lolo", count_bottom.toString())
            if(count_bottom == 0 ){
                empty_bottom = true
            }else{
                empty_bottom = false
            }

        }else {
            shoes_list= clothing
            count_shoes = getItemCount(clothing)
            Log.d("lolo", count_shoes.toString())
            if (count_shoes == 0) {
                empty_shoes = true
            } else {
                empty_shoes = false
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_outfit_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
        }

    toolbar_add_outfit_activity.setNavigationOnClickListener { onBackPressed() }
    }
}
