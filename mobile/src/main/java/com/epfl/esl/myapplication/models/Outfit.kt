package com.epfl.esl.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize

data class Outfit(
    val id_top: String = "",
    val id_trouser: String = "",
    val id_shoes: String = "",
    var id_outfit: String = "",

    val id_user:String="",

    val weather:String = "",
    val purpose: String = "",

    val uri_top: String = "",
    val uri_trouser: String = "",
    val uri_shoes: String = "",



    ):Parcelable