package com.joseqfonseca.myfav.model

data class Category(
    val id: String,
    val name: String,
    val picture: String,
    val domain_id: String,
    val domain_name: String,
    val category_id: String,
    val category_name: String,
    val attributes: ArrayList<Any?>
) {
}