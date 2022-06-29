package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.data.retrofit.CategoryRetrofit
import com.joseqfonseca.myfav.lib.Connection
import com.joseqfonseca.myfav.model.Category
import com.joseqfonseca.myfav.model.ProductResult

class CategoryRetrofitRepository : CategoryRepository {

    val retrofit = Connection.retrofitBuild.create(CategoryRetrofit::class.java)
    //val token = Connection.token

    override suspend fun getByPreditor(word: String): List<Category> {
        return retrofit.getByPreditor(word)
    }
}