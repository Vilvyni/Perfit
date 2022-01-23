package com.epfl.esl.myapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_clothing.*
import java.io.IOException

class AddClothesActivity : BaseActivity(), View.OnClickListener {

    private var mSelectedImageFileUri: Uri? = null

    private var mItemImageURL: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_clothing)

        setupActionBar()

        iv_add_update_clothing.setOnClickListener(this)
        btn_submit_add_clothing.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_add_update_clothing -> {

                    if (ContextCompat.checkSelfPermission(
                            this@AddClothesActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        Constants.showImageChooser(this@AddClothesActivity)

                    } else {

                        ActivityCompat.requestPermissions(
                            this@AddClothesActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )

                    }
                }

                R.id.btn_submit_add_clothing -> {
                    if (validateClothingDetails()) {
                        uploadClothingImage()
                    }
                }
            }
        }
    }

    fun clothingUploadSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this,
            resources.getString(R.string.item_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    fun imageUploadSuccess(imageURL: String) {
        mItemImageURL = imageURL
        uploadClothingDetails()
    }

    private fun uploadClothingDetails() {
        var tempSeason: String = ""
        var tempPurpose: String = ""
        var tempCate: String = ""

        if (rb_Spring_Summer.isChecked) {
            tempSeason = Constants.SUMMERSPRING
        } else {
            tempSeason = Constants.WINTERFALL
        }

        if (rb_sporty.isChecked) {
            tempPurpose = Constants.SPORTY
        } else if (rb_casual.isChecked) {
            tempPurpose = Constants.CAUSAL
        } else if (rb_formal.isChecked) {
            tempPurpose = Constants.FORMAL
        } else {
            tempPurpose = Constants.NIGHT
        }

        if (rb_top.isChecked) {
            tempCate = Constants.TOP
        } else if (rb_bottom.isChecked) {
            tempCate = Constants.BOTTOM
        } else {
            tempCate = Constants.SHOES
        }

        val clothing = Clothing(
            "",
            mItemImageURL,
            FirestoreClass().getCurrentUserID(),
            tempSeason,
            tempCate,
            tempPurpose,
            100,
            true
        )
        FirestoreClass().uploadClothingDetails(this@AddClothesActivity, clothing, tempCate)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {

            iv_add_update_clothing.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_vector_edit
                )
            )

            mSelectedImageFileUri = data.data!!

            try {
                GlideLoader(this).loadUserPicture(
                    mSelectedImageFileUri!!,
                    iv_clothing_image
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_clothing_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
        }

        toolbar_add_clothing_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateClothingDetails(): Boolean {


        return if (mSelectedImageFileUri == null) {
            showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
            false
        } else {
            true
        }
    }

    private fun uploadClothingImage() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().uploadImageToCloudStorage(
            this,
            mSelectedImageFileUri,
            Constants.ITEM_IMAGE
        )
    }
}