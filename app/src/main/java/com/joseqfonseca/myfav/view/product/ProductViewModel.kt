package com.joseqfonseca.myfav.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.service.ProductService
import kotlinx.coroutines.launch

class ProductViewModel(
    val product: Product
) : ViewModel() {

    fun addToFavorite() {

    }

    fun removeFromFavorite() {

    }
}