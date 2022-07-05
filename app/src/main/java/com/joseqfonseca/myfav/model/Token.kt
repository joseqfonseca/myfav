package com.joseqfonseca.myfav.model

data class Token(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val scope: String,
    val user_id: Long,
    val refresh_token: String
)