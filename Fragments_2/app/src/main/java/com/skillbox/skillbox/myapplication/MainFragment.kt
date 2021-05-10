package com.skillbox.skillbox.myapplication

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main), DialogData {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        typeOfArticleTextView.text = requireArguments().getString(KEY_TYPE)
        textOfTheArticleTextView.setText(requireArguments().getInt(KEY_TEXT))
        titleOfArticleTextView.setText(requireArguments().getInt(KEY_TITLE))

    }


    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_TEXT = "text"
        private const val KEY_TYPE = "type"
        fun newInstance(
            @StringRes title: Int,
            @StringRes textOfArticle: Int,
            typeOfArticle: ArticlesType
        ): MainFragment {
            return MainFragment().withArguments {
                putInt(KEY_TITLE, title)
                putInt(KEY_TEXT, textOfArticle)
                putString(KEY_TYPE, typeOfArticle.name)
            }
        }
    }

    override fun downloadDataToDialog(
        articlesToDialog: List<Article>,
        multiFourToDialog: BooleanArray,
        oceansTypesToDialog: Array<String>,
        activeListToDialog: MutableList<String>
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle("Select needed oceans")
            .setMultiChoiceItems(oceansTypesToDialog, multiFourToDialog) { _, which, isChecked ->
                multiFourToDialog[which] = isChecked
            }
            .setPositiveButton("Ok") { _, _ ->
                for (i in multiFourToDialog.indices) {
                    val checked = multiFourToDialog[i]
                    if (checked) {
                        activeListToDialog.add(oceansTypesToDialog[i])
                    }
                }
                Log.d("list", "$activeListToDialog")
                val newArticlesList = mutableListOf<Article>()
                articlesToDialog.forEach {
                    if (activeListToDialog.contains(it.typeOfArticle.toString())) {
                        newArticlesList.add(it)
                    }
                }
                Log.d("list", "$newArticlesList")
                val adapterDotsAfterChoice = DotsIndicatorPager2Adapter(articlesToDialog)
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


}