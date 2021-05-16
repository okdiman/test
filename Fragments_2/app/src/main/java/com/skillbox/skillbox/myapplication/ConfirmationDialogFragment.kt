package com.skillbox.skillbox.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment : DialogFragment() {

    private val selectedItems = ArrayList<ArticlesType>()
    private val checkedItems = BooleanArray(ArticlesType.values().size)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null) {
            val selectedTypes = arguments?.getString(KEY_TYPE) ?: ""
            if (selectedTypes.indexOf(",") > 0) {
                selectedTypes.split(",").forEach {
                    val type = ArticlesType.valueOf(it)
                    selectedItems.add(type)
                    checkedItems[type.ordinal] = true
                }
            }
        }
        return AlertDialog.Builder(requireContext())
            .setTitle("Select articles")
            .setMultiChoiceItems(
                ArticlesType.values().map { it.type }.toTypedArray(),
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
                parentFragment?.let { it as? DialogData }?.downloadDataToDialog(selectedItems)
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()
    }

    private fun getArticle(value: Int): ArticlesType {
        return ArticlesType.fromInt(value)
    }

    companion object {
        const val KEY_TYPE = "keyForType"
        const val TYPE = "Type"
        fun newInstance(selectedTypes: Array<ArticlesType>): ConfirmationDialogFragment {
            if (selectedTypes.isEmpty()) return ConfirmationDialogFragment()
            return ConfirmationDialogFragment().withArguments {
                putString(KEY_TYPE, selectedTypes.joinToString { "" })
            }
        }
    }
}

