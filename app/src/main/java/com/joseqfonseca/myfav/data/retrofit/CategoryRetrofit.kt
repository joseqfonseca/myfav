package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Category
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CategoryRetrofit {

    @GET("sites/MLB/domain_discovery/search")
    suspend fun getByPreditor(
        @Query("q") word: String,
        @Header("Authorization") token: String = ""
    ): List<Category>
}