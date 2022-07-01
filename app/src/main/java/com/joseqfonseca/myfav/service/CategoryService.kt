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

}