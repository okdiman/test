package com.skillbox.skillbox.myapplication

import androidx.annotation.StringRes

data class Article(
    @StringRes val titleOfArticle: Int,
    @StringRes val textOfArticle: Int,
    val typeOfArticle: ArticlesType
) {
}