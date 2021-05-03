package com.skillbox.skillbox.myapplication

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArticlesAdapter(
    private val articles: List<Article>,
    activity: MainActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return articles.size
    }

    override fun createFragment(position: Int): Fragment {
        val article: Article = articles[position]
        return MainFragment.newInstance(
            article.titleOfArticle,
            article.textOfArticle,
            article.typeOfArticle
        )
    }
}