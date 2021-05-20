package com.skillbox.skillbox.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val selectedTypes = arguments?.getBooleanArray(SELECTED)!!

        return AlertDialog.Builder(requireContext())
            .setTitle("Select articles")
            .setMultiChoiceItems(
                ArticlesType.values().map { it.type }.toTypedArray(),
                selectedTypes
            )
            { _, which, isChecked ->
                selectedTypes[which] = isChecked
            }
            .setPositiveButton("OK") { _, _ ->
                (activity as? DialogData)?.downloadDataToDialog(selectedTypes)
            }
            .setNegativeButton("Cancel") { _, _ ->
            }
            .create()
    }


    companion object {
        const val SELECTED = "selected"
        fun newInstance(selectedTypes: BooleanArray): ConfirmationDialogFragment {
            if (selectedTypes.isEmpty()) return ConfirmationDialogFragment()
            return ConfirmationDialogFragment().withArguments {
                putBooleanArray(SELECTED, selectedTypes)
            }
        }
    }
}

