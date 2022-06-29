package com.joseqfonseca.myfav.view.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.service.ProductService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val productService = ProductService()

    private val _listProduct = MutableLiveData<List<Product>>()

    val listProduct: LiveData<List<Product>> = _listProduct

    fun searchProductByFirstCategoryPredict(word: String) {
        viewModelScope.launch {
            _listProduct.value = null
            _listProduct.value =
                productService.searchProductByFirstCategoryPredict(word)
        }
    }
}