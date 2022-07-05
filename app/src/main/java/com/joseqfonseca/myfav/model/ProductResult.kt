package com.joseqfonseca.myfav.model

data class ProductResult(
    val results: List<Product>,
    val body: Product,
    val content: List<Product>,
    val plain_text: String
)