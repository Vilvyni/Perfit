package com.epfl.esl.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize

data class Outfit(
    var id_top: String = "",
    var id_bottom: String = "",
    var id_shoes: String = "",

    var id_user:String="",

    var weather:String = "",
    var purpose: String = "",

    ):Parcelable