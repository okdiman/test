package com.skillbox.skillbox.testonlineshop.features.detail.data.models

import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product

sealed class DetailsState{
    object Loading: DetailsState()
    object Error: DetailsState()
    class Success(val product: Product): DetailsState()
}
