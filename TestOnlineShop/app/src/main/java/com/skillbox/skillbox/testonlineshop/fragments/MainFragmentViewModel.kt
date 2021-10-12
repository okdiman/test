package com.skillbox.skillbox.testonlineshop.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.classes.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel: ViewModel() {
    private val repo = MainFragmentRepository()
    private val _productsStateFlow = MutableStateFlow<List<Product>>(emptyList())
    val productsStateFlow: StateFlow<List<Product>>
        get() = _productsStateFlow

    fun getAllProducts() {
        viewModelScope.launch {
            Log.i("listOfProducts", "start fun view model")
            repo.getAllProducts()
        }
    }
}