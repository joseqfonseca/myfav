package com.joseqfonseca.myfav.service

import android.util.Log
import com.joseqfonseca.myfav.model.Category
import com.joseqfonseca.myfav.repository.CategoryRetrofitRepository
import retrofit2.HttpException

class CategoryService {

    private val LOG_TAG = "CATEGORYSERVICE"
    private val categoryRepository = CategoryRetrofitRepository()

    private suspend fun getByPreditor(word: String): List<Category> {
        var categories = emptyList<Category>()

        try {
            categories = categoryRepository.getByPreditor(word)
        } catch (e: HttpException) {
            Log.e(LOG_TAG, "getByPreditor() : ${e.response()}")
        }

        return categories
    }

    suspend fun getCategoriesRandom(): List<Category> {
        var categories = mutableListOf<Category>()

        try {
            val categoriesIds = getAllCategories()

            categoriesIds.shuffled().subList(0, 9).forEach {
                val category = getCategoryById(it.id)

                category?.let {
                    categories.add(it)
                }
            }

        } catch (e: Exception) {
            Log.e(LOG_TAG, "getCategoriesRandom() : ${e.toString()}")
        }

        return categories
    }

    private suspend fun getAllCategories(): List<Category> {
        var categories = emptyList<Category>()

        try {
            categories = categoryRepository.getAllCategories()
        } catch (e: HttpException) {
            Log.e(LOG_TAG, "getAllCategories() : ${e.toString()}")
        }

        return categories
    }

    private suspend fun getCategoryById(categoryId: String): Category? {
        var category: Category? = null

        try {
            category = categoryRepository.getCategoryById(categoryId)
        } catch (e: HttpException) {
            Log.e(LOG_TAG, "getCategoryById() : ${e.toString()}")
        }

        return category
    }

}