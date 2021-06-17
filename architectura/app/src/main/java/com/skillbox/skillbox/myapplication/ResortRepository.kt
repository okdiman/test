package com.skillbox.skillbox.myapplication

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.skillbox.skillbox.myapplication.classes.Resorts
import kotlinx.android.synthetic.main.add_new_resort.view.*
import kotlin.random.Random

class ResortRepository {

    fun addResort(view: View, context: Context): Resorts {
        lateinit var newResort: Resorts
        val viewInside = (view as ViewGroup).inflate(R.layout.add_new_resort)

        AlertDialog.Builder(context)
            .setTitle("Add new resort")
            .setView(viewInside)
            .setPositiveButton("Add") { _, _ ->
                if (isNotEmptyFields(viewInside)) {
                    when (viewInside.selectTypesOfResort.selectedItem.toString()) {
                        "Mountain" -> newResort = Resorts.Mountain(
                            Random.nextLong(),
                            viewInside.addNameResortEditText.text.toString(),
                            viewInside.addCountryEditText.text.toString(),
                            viewInside.addPhotoEditText.text.toString(),
                            viewInside.addPlaceEditText.text.toString()
                        )
                        "Sea" -> newResort = Resorts.Sea(
                            Random.nextLong(),
                            viewInside.addNameResortEditText.text.toString(),
                            viewInside.addCountryEditText.text.toString(),
                            viewInside.addPhotoEditText.text.toString(),
                            viewInside.addPlaceEditText.text.toString()
                        )
                        "Ocean" -> newResort = Resorts.Ocean(
                            Random.nextLong(),
                            viewInside.addNameResortEditText.text.toString(),
                            viewInside.addCountryEditText.text.toString(),
                            viewInside.addPhotoEditText.text.toString(),
                            viewInside.addPlaceEditText.text.toString()
                        )
                    }
                } else {
                    Toast.makeText(
                        context,
                        "The form is incomplete, please, try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
        return newResort
    }

    //  удаление элемента
    fun deleteResort(resortsList: List<Resorts>, position: Int): List <Resorts> {
        return resortsList.filterIndexed { index, _ -> index != position } as ArrayList<Resorts>
    }

    //    проверка заполненности полей в диалоге
    private fun isNotEmptyFields(view: View): Boolean {
        return (view.addNameResortEditText.text.isNotEmpty()
                && view.addPlaceEditText.text.isNotEmpty()
                && view.addCountryEditText.text.isNotEmpty()
                && view.addPhotoEditText.text.isNotEmpty())
    }

}