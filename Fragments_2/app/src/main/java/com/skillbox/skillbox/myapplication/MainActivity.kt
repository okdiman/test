package com.skillbox.skillbox.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val articles: List<Article> = listOf(
        Article(
            titleOfArticle = R.string.BaliSeaTitle,
            textOfArticle = R.string.BaliSeaText,
            ArticlesType.PACIFIC
        ),
        Article(
            titleOfArticle = R.string.RedSeaTitle,
            textOfArticle = R.string.RedSeaText,
            ArticlesType.INDIAN
        ),
        Article(
            titleOfArticle = R.string.MediterraneanSeaTitle,
            textOfArticle = R.string.MediterraneanSeaText,
            ArticlesType.ATLANTIC
        ),
        Article(
            titleOfArticle = R.string.CaribbeanSeaTitle,
            textOfArticle = R.string.CaribbeanSeaText,
            ArticlesType.ATLANTIC
        ),
        Article(
            titleOfArticle = R.string.CoralSeaTitle,
            textOfArticle = R.string.CoralSeaText,
            ArticlesType.PACIFIC
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArticlesAdapter(articles, this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = articles[position].titleOfArticle.toString()
        }.attach()

    }
}