package com.epfl.esl.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize

data class Cloth(
    val id_top: String = "",
    val id_trouser: String = "",
    val id_shoes: String = "",

    val id_user:String="",

    val weather:String = "",
    val purpose: String = "",

    ):Parcelable