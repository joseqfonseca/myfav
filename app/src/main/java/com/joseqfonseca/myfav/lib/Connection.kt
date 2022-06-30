package com.joseqfonseca.myfav.lib

import android.content.SharedPreferences
import android.util.Log
import com.joseqfonseca.myfav.data.retrofit.TokenRetrofit
import com.joseqfonseca.myfav.model.Token
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Connection {
    private val LOG_TAG = "CONNECTION"
    val baseUrl = "https://api.mercadolibre.com/"
    //private var token: Token? = null

    val retrofitBuild: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            //.client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun getTokenByRetrofit(): String {
        /*try {
            //verificar se ainda está ativo pelo expires_in
            if (token == null)
                token = retrofitBuild.create(TokenRetrofit::class.java).getToken()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "getTokenByRetrofit" + e.toString())
        }
        return token?.access_token!!*/
        return "Bearer APP_USR-878200324327964-063015-c3aa7294e8c00fe1b06216b441718e1c-131272078"
    }
}