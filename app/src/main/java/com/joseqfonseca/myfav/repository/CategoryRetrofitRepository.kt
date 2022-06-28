package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.data.retrofit.CategoryRetrofit
import com.joseqfonseca.myfav.lib.Connection
import com.joseqfonseca.myfav.model.Category

class CategoryRetrofitRepository : CategoryRepository {

    val retrofit = Connection.retrofitBuild.create(CategoryRetrofit::class.java)

    override suspend fun getByPreditor(word: String): List<Category> {
        return retrofit.getByPreditor(word)
    }
}