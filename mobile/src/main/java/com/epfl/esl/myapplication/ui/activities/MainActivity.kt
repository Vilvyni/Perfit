package com.epfl.esl.myapplication.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sharedPreferences =
            getSharedPreferences(Constants.MYPERFIT_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
        // Set the result to the tv_main.
        tv_main.text= "The logged in user is $username."
        // END
    }
}