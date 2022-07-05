package com.joseqfonseca.myfav.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductPicture(
    val id: String,
    val url: String,
    val secure_url: String,
    val size: String,
    val max_size: String,
    val quality: String
):Parcelable