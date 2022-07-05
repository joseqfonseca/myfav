package com.joseqfonseca.myfav.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductAttribute(
    val id: String,
    val name: String,
    val value_id: String,
    val value_name: String,
    //val value_struct: String,
    //val values: List<Any>,
    val attribute_group_id: String,
    val attribute_group_name: String
) : Parcelable