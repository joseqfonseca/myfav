package com.joseqfonseca.myfav.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joseqfonseca.myfav.model.Category
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.service.CategoryService
import com.joseqfonseca.myfav.service.ProductService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val categoryService: CategoryService by lazy {
        CategoryService()
    }

    private val listCategory = MutableLiveData<List<Category>>()

    val _listCategory: LiveData<List<Category>> = listCategory

    fun loadCategories() {
        viewModelScope.launch {
            listCategory.value = null
            listCategory.value = categoryService.getCategoriesRandom()
        }
    }
}