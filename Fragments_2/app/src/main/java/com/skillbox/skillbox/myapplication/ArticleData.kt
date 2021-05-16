package com.skillbox.skillbox.myapplication

import androidx.annotation.StringRes

data class ArticleData(
    @StringRes val titleOfArticle: Int,
    @StringRes val textOfArticle: Int,
    val typeOfArticle: ArticlesType
) {
    companion object {
        fun getListOfArticleData(): List<ArticleData> {
            return listOf(
                ArticleData(
                    titleOfArticle = R.string.BlackSeaTitle,
                    textOfArticle = R.string.BlackSeaText,
                    ArticlesType.ATLANTIC
                ),
                ArticleData(
                    titleOfArticle = R.string.RedSeaTitle,
                    textOfArticle = R.string.RedSeaText,
                    ArticlesType.INDIAN
                ),
                ArticleData(
                    titleOfArticle = R.string.MediterraneanSeaTitle,
                    textOfArticle = R.string.MediterraneanSeaText,
                    ArticlesType.ATLANTIC
                ),
                ArticleData(
                    titleOfArticle = R.string.CaribbeanSeaTitle,
                    textOfArticle = R.string.CaribbeanSeaText,
                    ArticlesType.ATLANTIC
                ),
                ArticleData(
                    titleOfArticle = R.string.CoralSeaTitle,
                    textOfArticle = R.string.CoralSeaText,
                    ArticlesType.PACIFIC
                ),
                ArticleData(
                    titleOfArticle = R.string.DeadSeaTitle,
                    textOfArticle = R.string.DeadSeaText,
                    ArticlesType.SINGLE
                )
            )
        }
    }
}