package com.joseqfonseca.myfav.view.favorite

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.service.ProductService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val application: Application,
    private val productService: ProductService
) : ViewModel() {

    private val sharedPreferences =
        application.getSharedPreferences("FAVORITES", Context.MODE_PRIVATE)

    private var listFavoritesFinal = emptyList<Product>()

    private val listFavorites = MutableLiveData<List<Product>>()

    val _listFavorites: LiveData<List<Product>>
        get() {
            return listFavorites
        }

    private fun getListFavoritesLocal(): MutableSet<String> {
        // internal declaration because the favorites may change in another page and need to load each call
        return sharedPreferences.getStringSet("FAVORITES", mutableSetOf())!!
            .toMutableSet() //toMutable to resolve bug at restart app and erase all added before
    }

    fun loadFavorites() {
        val listFavoritesLocal = getListFavoritesLocal()

        viewModelScope.launch {
            listFavoritesFinal = productService.getFavorites(listFavoritesLocal)
            listFavorites.value = listFavoritesFinal
        }
    }

    fun removeFavorite(productId: String) {
        val listFavoritesLocal = getListFavoritesLocal()

        listFavoritesLocal.remove(productId)

        sharedPreferences.edit().putStringSet("FAVORITES", listFavoritesLocal).apply()

        loadFavorites()
    }

    fun filterByWord(word: String) {
        listFavorites.value = listFavoritesFinal.filter {
            println(it.title)
            it.title.contains(word, true)
        }
    }
}