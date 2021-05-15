package ru.android.viewpagerapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.android.viewpagerapp.data.ArticleTag
import ru.android.viewpagerapp.interfaces.PageActionInterface
import ru.android.viewpagerapp.withArguments

class ArticleDialog : DialogFragment() {

    private val selectedItems = ArrayList<ArticleTag>()
    private val checkedItems = BooleanArray(ArticleTag.values().size)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        if (arguments != null) {
            val selectedTags = arguments?.getString(KEY_TAG) ?: ""
            if (selectedTags.indexOf(",") > 0) {
                selectedTags.split(",").forEach {
                    val tag = ArticleTag.valueOf(it)
                    selectedItems.add(tag)
                    checkedItems[tag.ordinal] = true
                }
            }
        }


        return AlertDialog.Builder(requireContext())
            .setTitle("Select articles")
            .setMultiChoiceItems(
                ArticleTag.values().map { it.humanTag }.toTypedArray(),
                checkedItems
            )
            { _, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(getArticle(which))
                } else if (selectedItems.contains(getArticle(which))) {
                    selectedItems.remove(getArticle(which))
                }
            }
            .setPositiveButton("OK") { _, _ ->
                parentFragment?.let { it as? PageActionInterface }?.filterArticles(selectedItems)
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()
    }

    private fun getArticle(value: Int): ArticleTag {
        return ArticleTag.fromInt(value)
    }

    companion object {
        const val TAG = "ArticleDialogFragment"
        const val KEY_TAG = "tags"

        fun newInstance(selectedTags: Array<ArticleTag>): ArticleDialog {
            if (selectedTags.isEmpty()) return ArticleDialog()
            return ArticleDialog().withArguments {
                putString(KEY_TAG, selectedTags.joinToString(","))
            }

        }

    }


}