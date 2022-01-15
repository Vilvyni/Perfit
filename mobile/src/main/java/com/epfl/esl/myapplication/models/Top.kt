package com.epfl.esl.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize

data class Top(
    val id: String = "",
    val image: String = "",
    val id_user:String="",

    val weather:String = "",
    var cleaness:Int = 100,
    val purpose: String = "",
    val status:Boolean = true,

):Parcelable
