package com.joseqfonseca.myfav.service

import android.util.Log
import com.joseqfonseca.myfav.lib.Connection
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.repository.CategoryRetrofitRepository
import com.joseqfonseca.myfav.repository.ProductRetrofitRepository

class ProductService {

    private val LOG_TAG = "PRODUCTSERVICE"
    private val productRepository = ProductRetrofitRepository()
    private val categoryRepository = CategoryRetrofitRepository()

    suspend fun searchProductByFirstCategoryPredict(word: String): List<Product> {
        var products = emptyList<Product>()

        try {
            products = productRepository.getProductsByIds(
                productRepository.getHighlightsByCategory(
                    categoryRepository.getByPreditor(word).first().category_id
                ).filter{it.type == "ITEM"}.map { it.id }.joinToString()
            )
        } catch (e: Exception) {
            Log.e(LOG_TAG, "searchProductByFirstCategoryPredict() : ${e.toString()}")
        }

        return products
    }

}