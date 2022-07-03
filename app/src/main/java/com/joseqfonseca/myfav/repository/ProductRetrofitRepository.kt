package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.data.retrofit.ProductRetrofit
import com.joseqfonseca.myfav.lib.Connection
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.model.ProductResult
import javax.inject.Inject

class ProductRetrofitRepository @Inject constructor() : ProductRepository {
    val retrofit = Connection.retrofitBuild.create(ProductRetrofit::class.java)

    override suspend fun searchByCategoryId(categoryId: String): List<Product> {
        return retrofit.searchProductByCategoryId(categoryId).results
    }

    override suspend fun getProductsByIds(productIds: String): List<Product> {
        return retrofit.getProductsByIds(productIds).map {
            it.body
        }
    }

    override suspend fun getHighlightsByCategory(categoryId: String): List<Product> {
        return retrofit.getHighlightsByCategory(categoryId,Connection.getTokenByRetrofit()).content
    }
}