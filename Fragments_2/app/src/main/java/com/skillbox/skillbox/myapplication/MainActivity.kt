package com.skillbox.skillbox.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DialogData {

    private val articleData = ArticleData.getListOfArticleData()
    var selectedTypes = mutableListOf<ArticlesType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState?.getIntArray(MainFragment.TAG_KEY) != null) {
            val restoreType: IntArray = savedInstanceState.getIntArray(MainFragment.TAG_KEY)!!
            selectedTypes = restoreType.map { ArticlesType.fromInt(it) }.toMutableList()
            Log.d("qwer", "$selectedTypes")
            downloadDataToDialog(selectedTypes)
        } else {
            val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(articleData)
            viewPager.adapter = adapterDotsAfterChoice
            spring_dots_indicator.setViewPager2(viewPager)
            val adapterForChoice = ArticlesAdapter(articleData, MainActivity())
            viewPager.adapter = adapterForChoice
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(articleData[position].titleOfArticle)
            }.attach()
        }

        val adapterDots = DotsIndicatorPager2Adapter(articleData)
        viewPager.adapter = adapterDots
        spring_dots_indicator.setViewPager2(viewPager)

        val adapter = ArticlesAdapter(articleData, this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        val multiFour = booleanArrayOf(true, true, true, true)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(articleData[position].titleOfArticle)
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
            val oceansTypes = arrayOf(
                ArticlesType.ATLANTIC.toString(),
                ArticlesType.INDIAN.toString(),
                ArticlesType.PACIFIC.toString(),
                ArticlesType.SINGLE.toString()
            )

            val activeList = mutableListOf<String>()
            AlertDialog.Builder(this)
                .setTitle("Select needed oceans")
                .setMultiChoiceItems(oceansTypes, multiFour) { _, which, isChecked ->
                    multiFour[which] = isChecked
                }
                .setPositiveButton("Ok") { _, _ ->
                    for (i in multiFour.indices) {
                        val checked = multiFour[i]
                        if (checked) {
                            activeList.add(oceansTypes[i])
                        }
                    }
                    Log.d("list", "$activeList")
                    val newArticlesList = mutableListOf<ArticleData>()
                    articleData.forEach {
                        if (activeList.contains(it.typeOfArticle.toString())) {
                            newArticlesList.add(it)
                        }
                    }
                    Log.d("list", "$newArticlesList")
                    val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(newArticlesList)
                    viewPager.adapter = adapterDotsAfterChoice
                    spring_dots_indicator.setViewPager2(viewPager)
                    val adapterForChoice = ArticlesAdapter(newArticlesList, this)
                    viewPager.adapter = adapterForChoice
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        tab.text = resources.getString(newArticlesList[position].titleOfArticle)
                    }.attach()
                }
                .setNegativeButton("cancel", { _, _ -> })
                .show()
        }




        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(MainFragment.TAG_KEY, selectedTypes.map { it.ordinal }.toIntArray())
    }

    override fun downloadDataToDialog(articlesToDialog: MutableList<ArticlesType>) {

        val newArticlesList = mutableListOf<ArticleData>()
        articleData.forEach {
            if (it.typeOfArticle in articlesToDialog){
                newArticlesList.add(it)
            }
        }

        Log.d("qwer", "$newArticlesList")
        if (newArticlesList.isEmpty()) {
            android.app.AlertDialog.Builder(this)
                .setTitle("Nothing to show")
                .setMessage("Couldn't find any relevant types of oceans")
                .show()


            val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(articleData)
            viewPager?.adapter = adapterDotsAfterChoice
            viewPager?.let { spring_dots_indicator.setViewPager2(it) }
            val adapterForChoice = ArticlesAdapter(articleData, MainActivity())
            viewPager?.adapter = adapterForChoice
            viewPager?.let {
                TabLayoutMediator(tabLayout, it) { tab, position ->
                    tab.text = resources.getString(articleData[position].titleOfArticle)
                }.attach()
            }
        } else {
            val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(newArticlesList.toList())
            viewPager?.adapter = adapterDotsAfterChoice
            viewPager?.let { spring_dots_indicator.setViewPager2(it) }
            val adapterForChoice = ArticlesAdapter(newArticlesList.toList(), MainActivity())
            viewPager?.adapter = adapterForChoice
            viewPager?.let {
                TabLayoutMediator(tabLayout, it) { tab, position ->
                    tab.text =
                        resources.getString(newArticlesList.toList()[position].titleOfArticle)
                }.attach()
            }
        }
    }

    private fun showFilterDialog(typesToSelect: Array<ArticlesType>) {
        ConfirmationDialogFragment.newInstance(typesToSelect).show(
            supportFragmentManager, ConfirmationDialogFragment.TYPE
        )
    }


}