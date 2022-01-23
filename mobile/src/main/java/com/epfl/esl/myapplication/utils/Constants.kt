package com.epfl.esl.myapplication.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {


    // Firebase Constants
    // This  is used for the collection name for USERS and Items
    const val USERS: String = "users"
    const val ITEMS : String = "items"
    const val OUTFIT : String = "outfit"

    //OutfitContants
    const val URITOP: String = "URI_top"
    const val URIBOTTOM: String = "URI_bottom"
    const val URISHOES: String = "URI_shoes"

    const val GOCLOSET: String = "goToCloset"

    const val CHOSENTOP: String = "chosentop"
    const val CHOSENBOTTOM: String = "chosenbottom"
    const val CHOSENSHOES: String = "chosenshoes"

    const val IdClothing : String = "id_clothing"


    const val MYPERFIT_PREFERENCES: String = "MyShopPalPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    // Intent extra constants.
    const val EXTRA_USER_DETAILS: String = "extra_user_details"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
    const val READ_STORAGE_PERMISSION_CODE = 2

    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

    // Constant variables for Gender
    const val MALE: String = "Male"
    const val FEMALE: String = "Female"

    // Constant Season
    const val WINTERFALL: String = "Winter-Fall"
    const val SUMMERSPRING: String = "Summer-Spring"

    //Current Season //default
    const val WEATHER: String = "weather"

    const val SEASON: String = "season"




    // Constant purpose
    const val SPORTY: String = "sporty"
    const val CAUSAL: String = "causal"
    const val FORMAL: String = "formal"
    const val NIGHT: String = "night"

    const val PURPOSE: String = "purpose"
    const val CLEANLINESS: String = "cleanliness"


    const val SUGGESTION : String = "suss"


    // Constant category
    const val CATEGORY: String = "category"
    const val TOP: String = "top"
    const val BOTTOM: String = "bottom"
    const val SHOES: String = "shoes"

    // Firebase database field names
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"


    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"
    const val IMAGE: String = "image"

    const val COMPLETE_PROFILE: String = "profileCompleted"

    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"

    const val USER_ID: String = "user_id"
    const val ID_USER: String = "id_user"



    const val ITEM_IMAGE: String = "Item_Image"
    const val CLOTHING_IMAGE: String = "Clothing_Image"




    // END


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

    //  Create a function to get the extension of the selected image file.
    // START
    /**
     * A function to get the image file extension of the selected image.
     *
     * @param activity Activity reference.
     * @param uri Image file uri.
     */
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
    // END
}