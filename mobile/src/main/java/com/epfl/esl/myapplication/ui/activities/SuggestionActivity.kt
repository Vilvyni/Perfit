package com.epfl.esl.myapplication.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_add_clothing.*

class SuggestionActivity : BaseActivity(), View.OnClickListener{


    // A global variable for URI of a selected image from phone storage.
//    private var mSelectedImageFileUri: Uri? = null

    // A global variable for uploaded product image URL.
//    private var mItemImageURL: String = ""

    // A global variable for product id.
    private var season: String = ""
    private var purpose: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)

        if (intent.hasExtra(Constants.SEASON)) {
            season=
                intent.getStringExtra(Constants.SEASON)!!
        }

        if (intent.hasExtra(Constants.PURPOSE)) {
            purpose =
                intent.getStringExtra(Constants.PURPOSE)!!
        }

        Log.e("lolo",season + purpose)



//        setupActionBar()

        // Assign the click event to iv_add_update_product image and to to submit button.

//        iv_add_update_clothing.setOnClickListener(this)
//        btn_submit_add_clothing.setOnClickListener(this)


    }









    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}