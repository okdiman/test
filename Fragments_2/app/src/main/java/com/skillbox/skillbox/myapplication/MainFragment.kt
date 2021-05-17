package com.skillbox.skillbox.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main), DialogData {
    private val articleData: List<ArticleData> = ArticleData.getListOfArticleData()
    var selectedTypes: Array<ArticlesType> = ArticlesType.values()


    var viewPager = activity?.viewPager


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        typeOfArticleTextView.text = requireArguments().getString(KEY_TYPE)
        textOfTheArticleTextView.setText(requireArguments().getInt(KEY_TEXT))
        titleOfArticleTextView.setText(requireArguments().getInt(KEY_TITLE))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState?.getIntArray(TAG_KEY) != null) {
            val restoreType: IntArray = savedInstanceState.getIntArray(TAG_KEY)!!
            selectedTypes = restoreType.map { ArticlesType.fromInt(it) }.toTypedArray()
            Log.d("qwer","$selectedTypes" )
            downloadDataToDialog(selectedTypes.toList())
        } else {
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
        }
    }

    override fun downloadDataToDialog(articlesToDialog: List<ArticlesType>) {
        selectedTypes = articlesToDialog.toTypedArray()
        val filtredScreens = mutableSetOf<ArticleData>()



        articleData.forEach { data ->
            data.typeOfArticle.let { tag ->
                if (selectedTypes.contains(tag)) {
                    filtredScreens.add(data)
                }
            }
        }
        Log.d("qwer","$filtredScreens" )
        if (filtredScreens.isEmpty()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Nothing to show")
                .setMessage("Couldn't find any relevant types of oceans")
                .show()

            selectedTypes = ArticlesType.values()

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
            val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(filtredScreens.toList())
            viewPager?.adapter = adapterDotsAfterChoice
            viewPager?.let { spring_dots_indicator.setViewPager2(it) }
            val adapterForChoice = ArticlesAdapter(filtredScreens.toList(), MainActivity())
            viewPager?.adapter = adapterForChoice
            viewPager?.let {
                TabLayoutMediator(tabLayout, it) { tab, position ->
                    tab.text = resources.getString(filtredScreens.toList()[position].titleOfArticle)
                }.attach()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(TAG_KEY, selectedTypes.map { it.ordinal }.toIntArray())
    }

    private fun showFilterDialog(typesToSelect: Array<ArticlesType>) {
        ConfirmationDialogFragment.newInstance(typesToSelect).show(
            childFragmentManager, ConfirmationDialogFragment.TYPE
        )
    }

    companion object {
        const val TAG_KEY = "tags"
        private const val KEY_TITLE = "title"
        private const val KEY_TEXT = "text"
        private const val KEY_TYPE = "type"
        fun newInstance(
            @StringRes title: Int,
            @StringRes textOfArticle: Int,
            typeOfArticle: String
        ): MainFragment {
            return MainFragment().withArguments {
                putInt(KEY_TITLE, title)
                putInt(KEY_TEXT, textOfArticle)
                putString(KEY_TYPE, typeOfArticle)
            }
        }
    }

}