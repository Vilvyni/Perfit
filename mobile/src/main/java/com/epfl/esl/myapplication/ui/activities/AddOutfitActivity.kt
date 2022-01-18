package com.epfl.esl.myapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_outfit.*
import java.io.IOException

class AddOutfitActivity :BaseActivity(), View.OnClickListener{
    // A global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    // A global variable for uploaded product image URL.
    private var mItemImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_outfit)


        setupActionBar()

        // Assign the click event to iv_add_update_product image and to to submit button.

        iv_add_outfit_top.setOnClickListener(this)

        iv_add_outfit_bottom.setOnClickListener(this)

        iv_add_outfit_shoes.setOnClickListener(this)

    }



    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.iv_add_outfit_top -> {
                    val intent = Intent(this, ClothesSelectionActivity::class.java)
                    intent.putExtra(Constants.CATEGORY,Constants.TOP )
                    startActivity(intent)
                }
//
                R.id.iv_add_outfit_bottom -> {
                    val intent = Intent(this, ClothesSelectionActivity::class.java)
                    intent.putExtra(Constants.CATEGORY,Constants.BOTTOM )
                    startActivity(intent)
                }

                R.id.iv_add_outfit_shoes -> {
                    val intent = Intent(this, ClothesSelectionActivity::class.java)
                    intent.putExtra(Constants.CATEGORY,Constants.SHOES )
                    startActivity(intent)
                }

                R.id.btn_outfit_add -> {

                }
            }
        }
    }

//    fun OutfitUploadSuccess(){
//        hideProgressDialog()
//        Toast.makeText(
//            this,
//            resources.getString(R.string.item_uploaded_success_message),
//            Toast.LENGTH_SHORT
//        ).show()
//        finish()
//    }

//    fun imageUploadSuccess(imageURL: String) {
////        hideProgressDialog()
////        showErrorSnackBar("Item image is uploaded succesfully,Image URl:  $imageURL",false)
//
//        mItemImageURL = imageURL
//
//        uploadClothingDetails()
//    }

//    private fun uploadClothingDetails(){
//        var tempSeason:String = ""
//        var tempPurpose:String = ""
//        var tempCate:String = ""
//        val username =
//            this.getSharedPreferences(Constants.MYPERFIT_PREFERENCES, Context.MODE_PRIVATE)
//                .getString(Constants.LOGGED_IN_USERNAME, "")!!
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
//        val clothing = Clothing(
//            "",
//            mItemImageURL,
//            FirestoreClass().getCurrentUserID(),
//            tempSeason,
//            tempCate,
//            tempPurpose,
//            100,
//            true
//        )
//
//
//        println(tempSeason.toString())
//        Log.e("lolo", "heeere")
//        FirestoreClass().uploadClothingDetails(this@AddClothesActivity, clothing, tempCate)
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
//            //If permission is granted
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Constants.showImageChooser(this)
//            } else {
//                //Displaying another toast if permission is not granted
//                Toast.makeText(
//                    this,
//                    resources.getString(R.string.read_storage_permission_denied),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }
//
//
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK
//            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
//            && data!!.data != null
//        ) {
//
//            // Replace the add icon with edit icon once the image is selected.
//            iv_add_update_clothing.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.ic_vector_edit
//                )
//            )
//
//            // The uri of selection image from phone storage.
//            mSelectedImageFileUri = data.data!!
//
//            try {
//                // Load the product image in the ImageView.
//                GlideLoader(this).loadUserPicture(
//                    mSelectedImageFileUri!!,
//                    iv_clothing_image
//                )
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//
//
//
//
//    //allows the user to go back with the arrow bar
    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_outfit_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
        }

    toolbar_add_outfit_activity.setNavigationOnClickListener { onBackPressed() }
    }

//
//    private fun validateClothingDetails(): Boolean {
//
//
//        return if (mSelectedImageFileUri == null )  {
//            showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
//            false
//        }else {
//            true
//        }
//
//    }
//
//    private fun uploadClothingImage() {
//
//        showProgressDialog(resources.getString(R.string.please_wait))
//
//        //you could also add to the image the userID we'll see
//        FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri, Constants.ITEM_IMAGE)
//    }
//


}