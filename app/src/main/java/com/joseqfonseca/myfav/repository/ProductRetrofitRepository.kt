package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.data.retrofit.ProductRetrofit
import com.joseqfonseca.myfav.lib.Constants
import com.joseqfonseca.myfav.model.Product
import javax.inject.Inject

class ProductRetrofitRepository @Inject constructor(
    private val productRetrofit: ProductRetrofit
) : ProductRepository {

    val token = Constants.TOKEN

    override suspend fun searchByCategoryId(categoryId: String): List<Product> {
        return productRetrofit.searchProductByCategoryId(categoryId).results
    }

    override suspend fun getProductsByIds(productIds: String): List<Product> {
        return productRetrofit.getProductsByIds(productIds).map {
            it.body
        }
    }

    override suspend fun getHighlightsByCategory(categoryId: String): List<Product> {
        return productRetrofit.getHighlightsByCategory(categoryId, token).content
    }

    override suspend fun getDescriptionById(productId: String): String {
        return productRetrofit.getDescriptionById(productId).plain_text
    }
}