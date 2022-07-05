package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.model.Category

interface CategoryRepository {
    suspend fun getByPreditor(word: String): List<Category>
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoryById(categoryId: String): Category?
}