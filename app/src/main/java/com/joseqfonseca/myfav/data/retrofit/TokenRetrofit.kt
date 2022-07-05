package com.joseqfonseca.myfav.data.retrofit

import com.joseqfonseca.myfav.model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRetrofit {
    @FormUrlEncoded
    @POST("oauth/token?grant_type=authorization_code")
    suspend fun getToken(
        @Field("client_id") client_id: String = "878200324327964",
        @Field("client_secret") client_secret: String = "MCXPDVYgl9DFSZVfXopVuvoa8qJgNQli",
        @Field("code") code: String = "TG-62bb35fb918dff00137fe07f-131272078",
        @Field("redirect_uri") redirect_uri: String = "https://www.alkemy.org"
    ): Token
}