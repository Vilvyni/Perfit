package com.epfl.esl.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Clothing(

    var id_clothing: String = "",
    val image: String = "",
    val id_user: String = "",
    val season: String = "",
    val category: String = "",
    val purpose: String = "",
    val cleanliness: Int = 100 ,
    val availability: Boolean = true,

): Parcelable
