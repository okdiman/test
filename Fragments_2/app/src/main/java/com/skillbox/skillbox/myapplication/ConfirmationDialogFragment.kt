package com.skillbox.skillbox.myapplication

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class ConfirmationDialogFragment : DialogFragment() {

    private val dialogData: DialogData?
        get() = parentFragment.let { it as? DialogData }

    private fun donwloadDataDialog(
        articlesToDialog: List<Article>
    ) {
        dialogData?.downloadDataToDialog(
            articlesToDialog
        )
    }

}

