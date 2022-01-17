package com.epfl.esl.myapplication.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.epfl.esl.myapplication.R

class AddOutfitActivity :BaseActivity(), View.OnClickListener{
    // A global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    // A global variable for uploaded product image URL.
    private var mItemImageURL: String = ""



    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}