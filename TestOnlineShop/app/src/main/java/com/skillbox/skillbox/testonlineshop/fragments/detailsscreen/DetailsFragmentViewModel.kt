package com.skillbox.skillbox.testonlineshop.fragments.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.testonlineshop.fragments.Repository
import kotlinx.coroutines.launch

class DetailsFragmentViewModel : ViewModel() {
    private val repo = Repository()

    fun getDetailsProductInfo(){
        viewModelScope.launch {
            repo.getDetailsInfo()
        }
    }
}