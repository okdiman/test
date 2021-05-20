package com.skillbox.skillbox.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DialogData {

    lateinit var articleData: List<ArticleData>
    lateinit var selectedTypes: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedTypes = if (savedInstanceState == null) {
            BooleanArray(ArticlesType.values().size) { true }
        } else {
            savedInstanceState.getBooleanArray("TYPES")!!
        }
        initAdapter()

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
            showFilterDialog()
        }

        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("TYPES", selectedTypes)
    }

    override fun downloadDataToDialog(selectedTypes: BooleanArray) {
        this.selectedTypes = selectedTypes
        initAdapter()
    }

    private fun showFilterDialog() {
        ConfirmationDialogFragment.newInstance(selectedTypes.clone()).show(
            supportFragmentManager, "TAG"
        )
    }

    private fun initAdapter() {
        val articles = selectedTypes.mapIndexed { index, it -> if (it) getArticle(index) else null }
            .filterNotNull()
        articleData =
            ArticleData.getListOfArticleData().filter { articles.contains(it.typeOfArticle) }
        val adapterDots = ArticlesAdapter(articleData, this)
        viewPager.adapter = adapterDots
        spring_dots_indicator.setViewPager2(viewPager)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(articleData[position].titleOfArticle)
        }.attach()
    }

    private fun getArticle(value: Int): ArticlesType {
        return ArticlesType.fromInt(value)
    }

}