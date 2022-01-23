package com.epfl.esl.myapplication.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {

    const val USERS: String = "users"
    const val ITEMS: String = "items"
    const val OUTFIT: String = "outfit"

    const val CHOSENTOP: String = "chosentop"
    const val CHOSENBOTTOM: String = "chosenbottom"
    const val CHOSENSHOES: String = "chosenshoes"

    const val MYPERFIT_PREFERENCES: String = "MyPerfitPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    // Intent extra constants.
    const val EXTRA_USER_DETAILS: String = "extra_user_details"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
    const val READ_STORAGE_PERMISSION_CODE = 2

    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

    const val MALE: String = "Male"
    const val FEMALE: String = "Female"

    // Constant Season
    const val WINTERFALL: String = "Winter-Fall"
    const val SUMMERSPRING: String = "Summer-Spring"

    //Current Season
    const val WEATHER: String = "weather"
    const val SEASON: String = "season"

    // Constant purpose
    const val SPORTY: String = "sporty"
    const val CAUSAL: String = "causal"
    const val FORMAL: String = "formal"
    const val NIGHT: String = "night"
    const val PURPOSE: String = "purpose"
    const val CLEANLINESS: String = "cleanliness"


    // Constant category
    const val TOP: String = "top"
    const val BOTTOM: String = "bottom"
    const val SHOES: String = "shoes"

    // Firebase database field names
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"


    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val IMAGE: String = "image"

    const val COMPLETE_PROFILE: String = "profileCompleted"

    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"
    const val ID_USER: String = "id_user"


    const val ITEM_IMAGE: String = "Item_Image"

    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}