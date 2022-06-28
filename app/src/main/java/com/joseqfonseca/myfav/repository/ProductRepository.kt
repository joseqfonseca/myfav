package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.model.Product

interface ProductRepository {
    suspend fun searchByCategoryId(word: String): List<Product>
}