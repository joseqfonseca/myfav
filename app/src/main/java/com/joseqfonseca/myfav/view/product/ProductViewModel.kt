package com.joseqfonseca.myfav.view.product

import android.app.Application
import android.content.Context
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
class ProductViewModel @Inject constructor(
    private val application: Application,
    private val productService: ProductService
) : ViewModel() {

    private val sharedPreferences =
        application.getSharedPreferences("FAVORITES", Context.MODE_PRIVATE)

    lateinit var product: Product

    private val description = MutableLiveData<String>()

    val _description: LiveData<String>
        get() {
            return description
        }

    private val isFavorite = MutableLiveData<Boolean>()

    val _isFavorite: LiveData<Boolean>
        get() {
            return isFavorite
        }

    fun loadDescription() {
        viewModelScope.launch {
          description.value =  productService.getDescriptionById(product.id)
        }
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

        val action = listFavorites.add(product.id)

        if (!action)
            listFavorites.remove(product.id)

        sharedPreferences.edit().putStringSet("FAVORITES", listFavorites).apply()

        product.isFavorite = action

        isFavorite.value = action
    }

}