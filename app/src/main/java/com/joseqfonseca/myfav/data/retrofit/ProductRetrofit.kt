package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.model.ProductResult
import retrofit2.http.*

interface ProductRetrofit {

    @GET("sites/MLB/search?limit=1")
    suspend fun searchProductByCategoryId(
        @Query("category") categoryId: String
    ): ProductResult

    @GET("highlights/MLB/category/{categoryId}")
    suspend fun getHighlightsByCategory(
        @Path("categoryId") categoryId: String
    ): ProductResult

    @GET("items")
    suspend fun getProductsByIds(
        @Query("ids") productId: String
    ): List<ProductResult>

    @GET("items/{id}/description")
    suspend fun getDescriptionById(
        @Path("id") productId: String
    ): ProductResult
}