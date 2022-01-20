package com.epfl.esl.myapplication.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_add_clothing.*
import kotlinx.android.synthetic.main.activity_add_outfit.*
import kotlinx.android.synthetic.main.activity_suggestion.*
import android.content.Intent
import com.epfl.esl.myapplication.ui.fragments.OutfitsFragment


class SuggestionActivity : BaseActivity(), View.OnClickListener {


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


        btn_suggestion_choose_from_outfit.setOnClickListener(this)

        if (intent.hasExtra(Constants.SEASON)) {
            season =
                intent.getStringExtra(Constants.SEASON)!!
        }

        if (intent.hasExtra(Constants.PURPOSE)) {
            purpose =
                intent.getStringExtra(Constants.PURPOSE)!!
        }
        setupActionBar()
//        setupActionBar()


        Log.e("lolo", season + purpose)


// button listeners
        btn_suggestion_choose_from_outfit.setOnClickListener(this)
//        btn_suggestion_confirm.setOnClickListener(this)
        btn_suggestion_try_again.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.btn_suggestion_choose_from_outfit -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)

                }
//                R.id.btn_suggestion_confirm -> {
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    intent.putExtra(Constants.GOCLOSET, "goToCloset")
//                    startActivity(intent)
////
////
//                    }

                    R.id.btn_suggestion_try_again -> {
                        Log.d("lo","to delete")

                    }

                }
            }
        }


        private fun setupActionBar() {

            setSupportActionBar(toolbar_suggestion_activity)

            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
            }

            toolbar_suggestion_activity.setNavigationOnClickListener { onBackPressed() }
        }
    }

