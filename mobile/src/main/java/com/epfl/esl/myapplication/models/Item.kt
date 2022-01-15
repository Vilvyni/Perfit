package com.epfl.esl.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val stock_quantity: String = "",
    val image: String = "",
    val id: String = "",



//    val user_name: String = "",
//    val season: String = "",
//    val category: String = "",
//    val purpose: String = "",
//    val image: String = "",
//    val cleanliness: String = "",
//    val id: String = "",


): Parcelable

