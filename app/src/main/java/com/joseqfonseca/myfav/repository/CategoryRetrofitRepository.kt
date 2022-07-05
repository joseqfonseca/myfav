package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.data.retrofit.CategoryRetrofit
import com.joseqfonseca.myfav.model.Category
import javax.inject.Inject

class CategoryRetrofitRepository @Inject constructor(
    private val categoryRetrofit: CategoryRetrofit
) : CategoryRepository {

    override suspend fun getByPreditor(word: String): List<Category> {
        return categoryRetrofit.getByPreditor(word)
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryRetrofit.getAllCategories()
    }

    override suspend fun getCategoryById(categoryId: String): Category? {
        return categoryRetrofit.getCategoryById(categoryId)
    }
}