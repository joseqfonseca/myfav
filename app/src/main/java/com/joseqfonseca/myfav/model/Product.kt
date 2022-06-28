package com.joseqfonseca.myfav.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    val id: String,
    val title: String,
    val price: String,
    val description: String,
    val thumbnail: String,
    val available_quantity: String,
    val condition: String,
    val accepts_mercadopago: String
) : Parcelable