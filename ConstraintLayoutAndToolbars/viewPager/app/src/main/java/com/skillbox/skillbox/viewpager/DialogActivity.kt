package com.skillbox.skillbox.viewpager

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity(R.layout.activity_dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSimpleDialogButton.setOnClickListener {
            showSimpleDialog()
        }
        showButtonDialogButton.setOnClickListener {
            showButtonDialog()
        }
        showSingleChoiceButton.setOnClickListener {
            showSingleChoice()
        }
        showCustomDialog.setOnClickListener {
            showCustomDialog()
        }
        showDialogFragment.setOnClickListener {
            showDialogFragment()
        }
        showBottomSheet.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showSimpleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Simple Dialog")
            .setMessage("Simple Dialog message")

        dialog.show()
    }

    private fun showButtonDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete item")
            .setMessage("Are you sure?")
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { _, _ -> toast("clicked yes") })
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { _, _ -> toast("clicked no") })
            .setNeutralButton(
                "Neutral",
                DialogInterface.OnClickListener { _, _ -> toast("clicked neutral") })
            .create()
            .show()
    }

    private fun showSingleChoice() {
        val mailProviders = arrayOf("google", "yandex", "mail", "rambler")
        AlertDialog.Builder(this)
            .setTitle("Select mail provider")
            .setItems(mailProviders, { _, which -> toast("Selected = ${mailProviders[which]}") })
            .show()
    }

    private fun showCustomDialog() {
        AlertDialog.Builder(this)
            .setView(R.layout.dialog_attention)
            .setPositiveButton("ok", { _, _ -> })
            .show()
    }

    private fun showDialogFragment (){
        ConfirmationDialogFragment()
            .show(supportFragmentManager, "confirmation")
    }

    private fun showBottomSheet() {
        AvatarDialogFragment().show(supportFragmentManager, "Bottom")
    }

    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}