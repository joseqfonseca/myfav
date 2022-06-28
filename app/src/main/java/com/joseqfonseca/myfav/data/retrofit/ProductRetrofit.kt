package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.model.ProductResult
import com.joseqfonseca.myfav.model.Token
import retrofit2.Call
import retrofit2.http.*

interface ProductRetrofit {

    @GET("/sites/MLB/search?limit=30")
    suspend fun searchProductByCategoryId(
        @Query("category") categoryId: String,
        @Header("Authorization") token: String = ""
    ): ProductResult
}