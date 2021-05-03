package com.skillbox.skillbox.viewpager

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return  AlertDialog.Builder(requireContext())
            .setTitle("Delete item")
            .setMessage("Are you sure?")
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { _, _ -> })
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { _, _ -> })
            .setNeutralButton(
                "Neutral",
                DialogInterface.OnClickListener { _, _ -> })
            .create()
    }
}