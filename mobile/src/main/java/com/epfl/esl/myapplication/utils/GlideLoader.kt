package com.epfl.esl.myapplication.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.epfl.esl.myapplication.R
import java.io.IOException

class GlideLoader(val context: Context) {


    fun loadUserPicture(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image) // URI of the image
                .centerCrop() // Scale type of the image.
                .placeholder(R.drawable.ic_user_placeholder) // A default place holder if image is failed to load.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadItemPicture(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image) // URI of the image
                .centerCrop() // Scale type of the image.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
