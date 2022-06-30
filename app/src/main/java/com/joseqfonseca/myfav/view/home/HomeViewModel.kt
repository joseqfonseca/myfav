package com.joseqfonseca.myfav.view.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.service.ProductService
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val productService = ProductService()

    private val listProduct = MutableLiveData<List<Product>>()

    val _listProduct: LiveData<List<Product>> = listProduct

    private fun getListFavoritesLocal(): MutableSet<String> {
        // internal declaration because the favorites may change in another page and need to load each call
        return sharedPreferences.getStringSet("FAVORITES", mutableSetOf())!!
    }

    fun verifyFavorites() {
        val listFavorites = getListFavoritesLocal()

        listProduct.value = listProduct.value?.apply {
            this.forEach {
                it.isFavorite = listFavorites.contains(it.id)
            }
        }
    }

    fun setFavorite(productId: String) {
        val listFavorites = getListFavoritesLocal()

        val action = listFavorites.remove(productId)

        if (!action)
            listFavorites.add(productId)

        sharedPreferences.edit().putStringSet("FAVORITES", listFavorites).apply()

        listProduct.value = listProduct.value?.apply {
            this.forEach {
                if (it.id == productId)
                    it.isFavorite = !action
            }
        }
    }

    fun searchProductByFirstCategoryPredict(word: String) {
        val listFavorites = getListFavoritesLocal()

        //loading from api and verifying if is a favorite after return form api
        viewModelScope.launch {
            listProduct.value = null
            listProduct.value =
                productService.searchProductByFirstCategoryPredict(word).apply {
                    this.forEach {
                        it.isFavorite = listFavorites.contains(it.id)
                    }
                }
        }
    }
}