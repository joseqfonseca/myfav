package com.joseqfonseca.myfav.view.product

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joseqfonseca.myfav.model.Product

class ProductViewModel(
    val product: Product,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val isFavorite = MutableLiveData<Boolean>()

    val _isFavorite: LiveData<Boolean>
        get() {
            return isFavorite
        }

    init {
        //verifyFavorite()
    }

    private fun getListFavoritesLocal(): MutableSet<String> {
        // internal declaration because the favorites may change in another page and need to load each call
        return sharedPreferences.getStringSet("FAVORITES", mutableSetOf())!!
            .toMutableSet() //toMutable to resolve bug at restart app and erase all added before
    }

    fun verifyFavorite() {
        val listFavorites = getListFavoritesLocal()

        val isFav = listFavorites.contains(product.id)

        product.isFavorite = isFav

        isFavorite.value = isFav

    }

    fun setFavorite() {
        val listFavorites = getListFavoritesLocal()

        val action = listFavorites.remove(product.id)

        if (!action)
            listFavorites.add(product.id)

        sharedPreferences.edit().putStringSet("FAVORITES", listFavorites).apply()

        product.isFavorite = !action

        isFavorite.value = !action
    }

}