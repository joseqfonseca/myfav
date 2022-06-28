package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Token
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TokenRetrofit {
    @GET("https://api.mercadolibre.com/oauth/token?grant_type=authorization_code")
    fun getToken(
        @Query("client_id") client_id: String = "878200324327964",
        @Query("client_secret") client_secret: String = "MCXPDVYgl9DFSZVfXopVuvoa8qJgNQli",
        @Query("code") code: String = "TG-62b9eca56aa3660014bd0d35-131272078",
        @Query("redirect_uri") redirect_uri: String = "https://www.alkemy.org"
    ): Token
}