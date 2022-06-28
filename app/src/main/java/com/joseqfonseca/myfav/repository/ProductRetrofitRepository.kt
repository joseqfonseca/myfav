package com.joseqfonseca.myfav.repository

import com.joseqfonseca.myfav.data.retrofit.ProductRetrofit
import com.joseqfonseca.myfav.lib.Connection
import com.joseqfonseca.myfav.model.Product

class ProductRetrofitRepository : ProductRepository {
    val retrofit = Connection.retrofitBuild.create(ProductRetrofit::class.java)
    //val token = "APP_USR-878200324327964-062710-4371316993b52e54dd7b33b9ad9ff140-131272078"

    override suspend fun searchByCategoryId(category_id: String): List<Product> {
        return retrofit.searchProductByCategoryId(category_id).results
    }
}