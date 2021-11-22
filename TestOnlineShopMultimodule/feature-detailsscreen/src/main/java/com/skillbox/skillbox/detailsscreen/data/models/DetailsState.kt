package com.skillbox.skillbox.detailsscreen.data.models

import com.skillbox.skillbox.detailsscreen.domain.entities.Product

sealed class DetailsState{
    object Loading: DetailsState()
    object Error: DetailsState()
    class Success(val product: Product): DetailsState()
}
