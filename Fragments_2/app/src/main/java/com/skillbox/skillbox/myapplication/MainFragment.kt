package com.skillbox.skillbox.myapplication

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {
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
}