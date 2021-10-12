package com.skillbox.skillbox.testonlineshop.classes

import com.google.gson.annotations.SerializedName

data class ProductRequestResults(
    @SerializedName("")
    val results: List<Product>
)