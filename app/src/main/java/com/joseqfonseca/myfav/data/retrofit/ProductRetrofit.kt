package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.model.ProductResult
import retrofit2.http.*

interface ProductRetrofit {

    @GET("sites/MLB/search?limit=1")
    suspend fun searchProductByCategoryId(
        @Query("category") categoryId: String,
        @Header("Authorization") token: String = ""
    ): ProductResult

    @GET("highlights/MLB/category/{categoryId}")
    suspend fun getHighlightsByCategory(
        @Path("categoryId") categoryId: String,
        @Header("Authorization") token: String = ""
    ): ProductResult

    @GET("items")
    suspend fun getProductsByIds(
        @Query("ids") productId: String,
        @Header("Authorization") token: String = ""
    ): List<ProductResult>
}