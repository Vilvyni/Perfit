package com.epfl.esl.myapplication.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {


    // Firebase Constants
    // This  is used for the collection name for USERS.
    const val USERS: String = "users"

    const val MYPERFIT_PREFERENCES: String = "MyPerfitPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    // Intent extra constants.
    const val EXTRA_USER_DETAILS: String = "extra_user_details"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
    const val READ_STORAGE_PERMISSION_CODE = 2

    // Add a unique code to select the image from the storage. Using this code we will identify the image URI once it is selected.
    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

    const val MALE : String = "male"
    const val FEMALE : String = "female"

    const val MOBILE: String = "mobile"
    const val GENDER: String =  "gender"
    const val COMPLETE_PROFILE: String = "profileCompleted"
    const val IMAGE: String = "image"


    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"

    const val TOP: String = "top"
    const val TROUSER: String = "trouser"
    const val SHOES: String = "shoes"

    const val USER_ID: String = "id_user"
    const val PRODUCT_IMAGE: String = "image"
    const val WEATHER : String = "weather"
    const val SUMMER :String = "summer"
    const val WINTER :String = "winter"

    const val CLEANESS : String = "cleaness"
    const val STATUS : String = "status"

    const val PURPOSE : String = "purpose"
    const val SPORTY : String = "sporty"
    const val CAUSAL : String = "causal"
    const val FORMAL : String = "formal"
    const val NIGHT : String = "night"





    // Request the intent to select the image using the unique code.
    /**
     * A function for user profile image selection from phone storage.
     */
    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}