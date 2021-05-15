package ru.android.viewpagerapp.interfaces

import ru.android.viewpagerapp.data.ArticleTag

interface PageActionInterface {

    fun generateBadge()

    fun filterArticles(tags: List<ArticleTag>)
}