package com.joseqfonseca.myfav.view.home

import androidx.lifecycle.*
import com.joseqfonseca.myfav.model.Category
import com.joseqfonseca.myfav.model.Product
import com.joseqfonseca.myfav.service.CategoryService
import com.joseqfonseca.myfav.service.ProductService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val categoryService: CategoryService
): ViewModel() {

    private val listCategory = MutableLiveData<List<Category>>()

    val _listCategory: LiveData<List<Category>> = listCategory

    fun loadCategories() {
        viewModelScope.launch {
            listCategory.value = null
            listCategory.value = categoryService.getCategoriesRandom()
        }
    }
}