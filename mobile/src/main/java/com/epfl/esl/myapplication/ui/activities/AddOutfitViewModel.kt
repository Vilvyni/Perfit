package com.epfl.esl.myapplication.ui.activities

import android.util.Log
import androidx.lifecycle.ViewModel


class AddOutfitViewModel: ViewModel() {

    var top_image_URI: String?
    var bottom_image_URI: String?
    var shoes_image_URI: String?
    var top_id: String?
    var bottom_id: String?
    var shoes_id: String?

    init {
        top_image_URI = "a"
        bottom_image_URI = "b"

        shoes_image_URI = "c"

        top_id = "d"

        bottom_id = "e"
        shoes_id = "f"

    }

    fun printData(){
        Log.d( "lolo", top_id.toString())
        Log.d( "lolo", shoes_id.toString())
    }
}

