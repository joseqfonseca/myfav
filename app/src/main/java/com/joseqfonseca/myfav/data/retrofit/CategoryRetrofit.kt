package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Category
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryRetrofit {

    @GET("sites/MLB/domain_discovery/search")
    suspend fun getByPreditor(
        @Query("q") word: String,
    ): List<Category>

    @GET("sites/MLB/categories")
    suspend fun getAllCategories(
    ): List<Category>

    @GET("categories/{id}")
    suspend fun getCategoryById(
        @Path("id") categoryId: String,
    ): Category?
}