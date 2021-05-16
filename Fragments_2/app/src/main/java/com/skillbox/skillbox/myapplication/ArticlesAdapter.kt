package com.skillbox.skillbox.myapplication

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArticlesAdapter(
    private val articleData: List<ArticleData>,
    activity: MainActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return articleData.size
    }

    override fun createFragment(position: Int): Fragment {
        val articleData: ArticleData = articleData[position]
        return MainFragment.newInstance(
            articleData.titleOfArticle,
            articleData.textOfArticle,
            articleData.typeOfArticle
        )
    }
}