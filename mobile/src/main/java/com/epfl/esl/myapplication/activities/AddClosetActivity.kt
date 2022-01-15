package com.epfl.esl.myapplication.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.databinding.ActivityAddClosetBinding
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Cloth
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_add_closet.*

class AddClosetActivity : BaseActivity(), View.OnClickListener {

    private var mSelectedImageFileUri: Uri? = null
    private var mProductImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        // Assign the click event to iv_add_update_product image.
        pick_item_from_gallery.setOnClickListener(this)

        // confirm the choice of image
        confirm_add_closet.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        if(v!=null){
            when(v.id){
                R.id.pick_item_from_gallery ->{

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@AddClosetActivity)
                    } else {
                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.confirm_add_closet -> {
                    if (validateProductDetails()) {

                        uploadProductImage()
                    }

                }
            }
        }

    }
    private fun validateProductDetails(): Boolean {
        return when {

            mSelectedImageFileUri == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            else -> {
                true
            }
        }
    }
    private fun uploadProductImage() {

        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().uploadImageToCloudStorage(
            this@AddClosetActivity,
            mSelectedImageFileUri,
            Constants.PRODUCT_IMAGE
        )
    }
    fun imageUploadSuccess(imageURL: String) {

        // Initialize the global image url variable.
        mProductImageURL = imageURL

        updateItemDetails()
    }

    private fun updateItemDetails() {
        var tempSeason:String = ""
        var tempPurpose:String = ""
        var tempCate:String = ""
        val username =
            this.getSharedPreferences(Constants.MYPERFIT_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME, "")!!

        // season
        if(hot.isChecked){
            tempSeason = Constants.SUMMER
        }
        else{
            tempSeason = Constants.WINTER
        }

        // purpose
        if(sporty.isChecked){
            tempPurpose = Constants.SPORTY
        }
        else if(causal.isChecked){
            tempPurpose = Constants.CAUSAL
        }
        else if(formal.isChecked){
            tempPurpose = Constants.FORMAL
        }
        else {
            tempPurpose = Constants.NIGHT
        }

        if(pull.isChecked){
            tempCate = Constants.TOP
        }
        else if(trouser.isChecked){
            tempCate = Constants.TROUSER
        }
        else{
            tempCate = Constants.SHOES
        }


        val cloth = Cloth(
            "",
            mProductImageURL,
            FirestoreClass().getCurrentUserID(),
            tempSeason,
            100,
            tempPurpose,
            true
        )

        FirestoreClass().uploadProductDetails(this@AddClosetActivity, cloth,tempCate)

    }


    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_product_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_add_product_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun productUploadSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(
            this@AddClosetActivity,
            resources.getString(R.string.product_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}