package com.joseqfonseca.myfav.service

import android.util.Log
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.repository.CategoryRetrofitRepository
import com.joseqfonseca.myfav.repository.ProductRetrofitRepository
import retrofit2.HttpException
import javax.inject.Inject

class ProductService @Inject constructor(
    private val productRepository: ProductRetrofitRepository,
    private val categoryRepository: CategoryRetrofitRepository
) {

    private val LOG_TAG = "PRODUCTSERVICE"

    suspend fun searchProductByFirstCategoryPredict(word: String): List<Product> {
        var products = emptyList<Product>()

        try {
            val categoryIdByPreditor = getByPreditor(word)

            val highlightsProductsIds = getHighlightsByCategory(categoryIdByPreditor)
                .filter { it.type == "ITEM" }
                .map { it.id }
                .joinToString()

            products = getProductsByIds(highlightsProductsIds)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "searchProductByFirstCategoryPredict() : ${e.message}")
        }

        return products
    }

    private suspend fun getByPreditor(word: String): String {
        var predictor: String

        try {
            predictor = categoryRepository.getByPreditor(word).first().category_id
        } catch (e: HttpException) {
            throw Exception("getByPreditor() : ${e.response()}")
        }

        return predictor
    }

    private suspend fun getHighlightsByCategory(categoryId: String): List<Product> {
        var list: List<Product>

        try {
            list = productRepository.getHighlightsByCategory(categoryId)
        } catch (e: HttpException) {
            throw Exception("getHighlightsByCategory() : ${e.response()}")
        }

        return list
    }

    private suspend fun getProductsByIds(productIds: String): List<Product> {
        var list: List<Product>

        try {
            list = productRepository.getProductsByIds(productIds)
        } catch (e: HttpException) {
            throw Exception("getProductsByIds() : ${e.response()}")
        }

        return list
    }

    suspend fun getFavorites(listFavoritesLocal: Set<String>): List<Product> {
        var list = listOf<Product>()

        try {
            list = productRepository.getProductsByIds(listFavoritesLocal.joinToString())
        } catch (e: Exception) {
            Log.e(LOG_TAG, "getFavorites() : ${e.toString()}")
        }

        return list
    }

}