package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.model.ProductResult

interface ProductRepository {
    suspend fun searchByCategoryId(word: String): List<Product>
    suspend fun getProductsByIds(productIds: String): List<Product>
    suspend fun getHighlightsByCategory(categoryId: String): List<Product>
}