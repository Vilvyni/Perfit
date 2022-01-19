package com.epfl.esl.myapplication.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.models.Outfit
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_clothing.*
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

    var id_top:String = "none"
    var id_bottom:String = "none"
    var id_shoes:String = "none"

    var URI_top:String = "none"
    var URI_bottom:String = "none"
    var URI_shoes:String = "none"


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
        btn_outfit_add.setOnClickListener(this)



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
                    GlideLoader(this).loadItemPicture(model.image,iv_add_outfit_top)
                    URI_top = model.image
                    id_top = model.id_clothing
                    Log.e("loloo",index_top.toString())
                }
//
                R.id.bottom_left -> {
                    index_bottom = index_bottom-1
                    //if it is equal to 0
                    if(index_bottom<0 ) {index_bottom=count_bottom-1}

                    var model = bottom_list[index_bottom]
                    GlideLoader(this).loadItemPicture(model.image,iv_add_outfit_bottom)
                    URI_bottom = model.image
                    id_bottom = model.id_clothing
                    Log.e("loloo",index_bottom.toString())

                }

                R.id.shoes_left -> {
                    index_shoes = index_shoes-1
                    //if it is equal to 0
                    if(index_shoes<0 ) {index_shoes=count_shoes-1}

                    var model = shoes_list[index_shoes]
                    GlideLoader(this).loadItemPicture(model.image,iv_add_outfit_shoes)
                    URI_shoes = model.image
                    id_shoes = model.id_clothing
                    Log.e("loloo",index_shoes.toString())

                }
                
                R.id.top_right -> {
                    index_top = index_top+1
                    //if it is equal to 0
                    if(index_top==count_top ) {index_top=0}

                    var model = top_list[index_top]
                    GlideLoader(this).loadItemPicture(model.image,iv_add_outfit_top)
                    URI_top= model.image
                    id_top = model.id_clothing
                    Log.e("loloo",index_top.toString())
                }
//
                R.id.bottom_right -> {
                    index_bottom = index_bottom+1
                    //if it is equal to 0
                    if(index_bottom==count_bottom ) {index_bottom=0}

                    var model = bottom_list[index_bottom]
                    GlideLoader(this).loadItemPicture(model.image,iv_add_outfit_bottom)
                    URI_bottom = model.image
                    id_bottom = model.id_clothing
                    Log.e("loloo",index_bottom.toString())

                }

                R.id.shoes_right -> {
                    index_shoes = index_shoes+1
                    //if it is equal to 0
                    if(index_shoes==count_shoes ) {index_shoes=0}

                    var model = shoes_list[index_shoes]
                    GlideLoader(this).loadItemPicture(model.image,iv_add_outfit_shoes)
                    URI_shoes = model.image
                    id_shoes = model.id_clothing
                    Log.e("loloo",index_shoes.toString())

                }

                R.id.btn_outfit_add -> {

                    if(verify_oufit())
                        showProgressDialog(resources.getString(R.string.please_wait))

                        uploadOutfit()


                }
            }
        }
    }
    fun uploadOutfit() {
        var tempSeason:String = ""
        var tempPurpose:String = ""
        if(rb_outfit_Spring_Summer.isChecked){
            tempSeason = Constants.SUMMERSPRING
        }
        else{
            tempSeason = Constants.WINTERFALL
        }

        // purpose
        if(rb_outfit_sporty.isChecked){
            tempPurpose = Constants.SPORTY
        }
        else if(rb_outfit_casual.isChecked){
            tempPurpose = Constants.CAUSAL
        }
        else if(rb_outfit_formal.isChecked){
            tempPurpose = Constants.FORMAL
        }
        else {
            tempPurpose = Constants.NIGHT
        }



        val outfit = Outfit(
            id_top,
            id_bottom,
            id_shoes,
            "",
            FirestoreClass().getCurrentUserID(),
            tempSeason,
            tempPurpose,
            URI_top,
            URI_bottom,
            URI_shoes

            
        )


        FirestoreClass().uploadOutfitDetails(this@AddOutfitActivity, outfit)
    }


    fun outfitUploadSuccess(){
        hideProgressDialog()
        Toast.makeText(
            this,
            "Your outfit is uploaded.",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
    fun verify_oufit() :Boolean {
        if (empty_top or empty_bottom or empty_shoes) {
            Toast.makeText(
                this,
                "Add clothes in closet",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (id_top == "none") {
            Toast.makeText(
                this,
                "Select top",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (id_bottom == "none") {
            Toast.makeText(
                this,
                "Select bottom",
                Toast.LENGTH_SHORT
            ).show()
            return false

        } else if (id_shoes == "none") {
            Toast.makeText(
                this,
                "Select shoes",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }


//
//        // season
//        if(rb_Spring_Summer.isChecked){
//            tempSeason = Constants.SUMMERSPRING
//        }
//        else{
//            tempSeason = Constants.WINTERFALL
//        }
//
//        // purpose
//        if(rb_sporty.isChecked){
//            tempPurpose = Constants.SPORTY
//        }
//        else if(rb_casual.isChecked){
//            tempPurpose = Constants.CAUSAL
//        }
//        else if(rb_formal.isChecked){
//            tempPurpose = Constants.FORMAL
//        }
//        else {
//            tempPurpose = Constants.NIGHT
//        }
//
//        if(rb_top.isChecked){
//            tempCate = Constants.TOP
//        }
//        else if(rb_bottom.isChecked){
//            tempCate = Constants.BOTTOM
//        }
//        else{
//            tempCate = Constants.SHOES
//        }
//
//    }

    fun getItemCount(list:ArrayList<Clothing>): Int {
        return list.size
    }

    fun successItemsListFromFireStoreClothes(clothing: ArrayList<Clothing>,category:String) {
        if(category == Constants.TOP){
            top_list= clothing
            count_top = getItemCount(clothing)
            if(count_top == 0 ){
                empty_top = true
            }else{
                empty_top = false
            }



        }else if(category == Constants.BOTTOM){
            bottom_list= clothing
            count_bottom = getItemCount(clothing)
            if(count_bottom == 0 ){
                empty_bottom = true
            }else{
                empty_bottom = false
            }

        }else {
            shoes_list= clothing
            count_shoes = getItemCount(clothing)
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
