package com.joseqfonseca.myfav.lib

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Connection {
    val baseUrl = "https://api.mercadolibre.com/"

    val retrofitBuild: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            //.client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}