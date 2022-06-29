package com.joseqfonseca.myfav.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
class Product(
    val id: String,
    val title: String,
    val price: String,
    val description: String,
    val thumbnail: String,
    val type: String,
    val available_quantity: String,
    val condition: String,
    val accepts_mercadopago: String,
    val pictures: @RawValue List<ProductPicture>,
    val attributes: @RawValue List<ProductAttribute>,
    var isFavorite: Boolean
) : Parcelable