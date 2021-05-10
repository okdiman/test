package com.skillbox.skillbox.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val articles: List<Article> = listOf(
        Article(
            titleOfArticle = R.string.BlackSeaTitle,
            textOfArticle = R.string.BlackSeaText,
            ArticlesType.ATLANTIC
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
        ),
        Article(
            titleOfArticle = R.string.DeadSeaTitle,
            textOfArticle = R.string.DeadSeaText,
            ArticlesType.SINGLE
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapterDots = DotsIndicatorPager2Adapter(articles)
        viewPager.adapter = adapterDots
        spring_dots_indicator.setViewPager2(viewPager)

        val adapter = ArticlesAdapter(articles, this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        val multiFour = booleanArrayOf(true, true, true, true)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(articles[position].titleOfArticle)
        }.attach()

        viewPager.setPageTransformer { page, position ->
            when {
                position < -1 || position > 1 -> {
                    page.alpha = 0f
                }
                position <= 0 -> {
                    page.alpha = 1 + position
                }
                position <= 1 -> {
                    page.alpha = 1 - position
                }
            }
        }
        chooseThemeButton.setOnClickListener {
            ConfirmationDialogFragment()
                .show(supportFragmentManager, "ConfirmDialogFragment")
//            val oceansTypes = arrayOf(
//                ArticlesType.ATLANTIC.toString(),
//                ArticlesType.INDIAN.toString(),
//                ArticlesType.PACIFIC.toString(),
//                ArticlesType.SINGLE.toString()
//            )
//
//            val activeList = mutableListOf<String>()
//            AlertDialog.Builder(this)
//                .setTitle("Select needed oceans")
//                .setMultiChoiceItems(oceansTypes, multiFour) { _, which, isChecked ->
//                    multiFour[which] = isChecked
//                }
//                .setPositiveButton("Ok") { _, _ ->
//                    for (i in multiFour.indices) {
//                        val checked = multiFour[i]
//                        if (checked) {
//                            activeList.add(oceansTypes[i])
//                        }
//                    }
//                    Log.d("list", "$activeList")
//                    val newArticlesList = mutableListOf<Article>()
//                    articles.forEach {
//                        if (activeList.contains(it.typeOfArticle.toString())) {
//                            newArticlesList.add(it)
//                        }
//                    }
//                    Log.d("list", "$newArticlesList")
//                    val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(articles)
//                    viewPager.adapter = adapterDotsAfterChoice
//                    spring_dots_indicator.setViewPager2(viewPager)
//                    val adapterForChoice = ArticlesAdapter(newArticlesList, this)
//                    viewPager.adapter = adapterForChoice
//                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                        tab.text = resources.getString(newArticlesList[position].titleOfArticle)
//                    }.attach()
//                }
//                .setNegativeButton("cancel", { _, _ -> })
//                .show()
        }

        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }
    }
}