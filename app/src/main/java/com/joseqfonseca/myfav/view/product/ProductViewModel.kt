package com.joseqfonseca.myfav.view.product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joseqfonseca.myfav.model.Product

class ProductViewModel(
    val product: Product,
    val context: Context
) : ViewModel() {

    private val productLiveData = MutableLiveData<Product>()

    private val sharedPreferences = context.getSharedPreferences("FAVORITES", Context.MODE_PRIVATE)

    private val listFavorites = sharedPreferences.getStringSet("FAVORITES", mutableSetOf())!!

    val _product: LiveData<Product>
        get() {
            return productLiveData
        }

    init {
        verifyFavorite()
    }

    private fun verifyFavorite() {
        product.let {
            it.isFavorite = listFavorites.contains(it.id)
        }
    }

    fun setFavorite() {


        val action = listFavorites.remove(product.id)

        if (!action)
            listFavorites.add(product.id)

        sharedPreferences.edit().putStringSet("FAVORITES", listFavorites).apply()

        product.isFavorite = !action

        productLiveData.value = product
    }

}