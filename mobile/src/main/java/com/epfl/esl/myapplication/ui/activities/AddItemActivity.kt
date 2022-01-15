package com.epfl.esl.myapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import com.epfl.esl.myapplication.R
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.IOException

class AddItemActivity : BaseActivity(), View.OnClickListener {

    // A global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    // A global variable for uploaded product image URL.
    private var mItemImageURL: String = ""

//    private var _binding : ActivityAddItemBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,container:ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ActivityAddItemBinding.inflate(inflater,container,false)
//
//
//        binding.autoCompleteTextView.setAdapter(arrayAdapter)
//        return binding.root
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        setupActionBar()

        // Assign the click event to iv_add_update_product image and to to submit button.
        iv_add_update_item.setOnClickListener(this)
        btn_submit_add_item.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {

                // if the user presses on the new photo
                // The permission code is similar to the user profile image selection.
                R.id.iv_add_update_item -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@AddItemActivity)
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

                R.id.btn_submit_add_item -> {
                    if (validateProductDetails()) {
                        uploadItemImage()

                    }
                }
            }
        }
    }

    fun itemUploadSuccess(){
        hideProgressDialog()
        Toast.makeText(
            this,
            resources.getString(R.string.item_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()
        finish()


    }

    fun imageUploadSuccess(imageURL: String) {
//        hideProgressDialog()
//        showErrorSnackBar("Item image is uploaded succesfully,Image URl:  $imageURL",false)

        mItemImageURL = imageURL

        uploadItemDetails()
    }

    //we got the items details
    private fun uploadItemDetails(){
        val username = this.getSharedPreferences(Constants.MYPERFIT_PREFERENCES, Context.MODE_PRIVATE)
            .getString(Constants.LOGGED_IN_USERNAME,"")!!

        //Making object where I am passing all the Items objects
        // editable text on the xml
        //TODO change this to droppdown menu values, we get it from the xml
        val item = Item(
            FirestoreClass().getCurrentUserID(),
            username,
            et_product_title.text.toString().trim{ it <= ' '},
            et_product_price.text.toString().trim{ it <= ' '},
            et_product_description.text.toString().trim{ it <= ' '},
            et_product_quantity.text.toString().trim{ it <= ' '},
            mItemImageURL
        )

        FirestoreClass().uploadItemDetails(this, item)

    }

    /**
     * This function will identify the result of runtime permission after the user allows or deny permission based on the unique code.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
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

            // Replace the add icon with edit icon once the image is selected.
            iv_add_update_item.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_vector_edit
                )
            )

            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data!!

            try {
                // Load the product image in the ImageView.
                GlideLoader(this).loadUserPicture(
                    mSelectedImageFileUri!!,
                    iv_product_image
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_product_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
        }

        toolbar_add_product_activity.setNavigationOnClickListener { onBackPressed() }
    }


    // TODO : CHANGE THIS TO OUR APP
    /**
     * A function to validate the product details.
     */
    private fun validateProductDetails(): Boolean {
        return when {

            mSelectedImageFileUri == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(et_product_title.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_title), true)
                false
            }

            TextUtils.isEmpty(et_product_price.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_price), true)
                false
            }

            TextUtils.isEmpty(et_product_description.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_description),
                    true
                )
                false
            }

            TextUtils.isEmpty(et_product_quantity.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_quantity),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to upload the selected item image to firebase cloud storage.
     */
    private fun uploadItemImage() {

        showProgressDialog(resources.getString(R.string.please_wait))

        //you could also add to the image the userID we'll see
        FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri, Constants.ITEM_IMAGE)
    }
//
//    /**
//     * A function to get the successful result of product image upload.
//     */
//    fun imageUploadSuccess(imageURL: String) {
//
//        // Initialize the global image url variable.
//        mProductImageURL = imageURL
//
//        uploadProductDetails()
//    }
//
//    private fun uploadProductDetails() {
//
//        // Get the logged in username from the SharedPreferences that we have stored at a time of login.
//        val username =
//            this.getSharedPreferences(Constants.MYPERFIT_PREFERENCES, Context.MODE_PRIVATE)
//                .getString(Constants.LOGGED_IN_USERNAME, "")!!
//
//        // Here we get the text from editText and trim the space
//        val item = Item(
//            FirestoreClass().getCurrentUserID(),
//            username,
//            et_product_title.text.toString().trim { it <= ' ' },
//            et_product_price.text.toString().trim { it <= ' ' },
//            et_product_description.text.toString().trim { it <= ' ' },
//            et_product_quantity.text.toString().trim { it <= ' ' },
//            mItemImageURL
//        )
//
//        FirestoreClass().uploadItemDetails(this@ItemActivity, item)
//    }
//
//    /**
//     * A function to return the successful result of Product upload.
//     */
//    fun productUploadSuccess() {
//
//        // Hide the progress dialog
//        hideProgressDialog()
//
//        Toast.makeText(
//            this@AddItemActivity,
//            resources.getString(R.string.item_uploaded_success_message),
//            Toast.LENGTH_SHORT
//        ).show()
//
//        finish()
//    }
}