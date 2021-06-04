package com.skillbox.skillbox.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.add_new_resort.view.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment() : Fragment(R.layout.list_fragment) {

    private var resortsAdapter: ResortsAdapter? = null
    var isChecked: Boolean = false

    //        private var resortsList = emptyArray<Resorts>()
    private var resortsList = arrayListOf<Resorts>(
        Resorts.Mountain(
            name = "Aspen, Colorado",
            country = "USA",
            photo = R.drawable.aspen,
            mountain = "Aspen"
        ),
        Resorts.Ocean(
            name = "Hawaiian Islands",
            country = "USA",
            photo = R.drawable.hawaii,
            ocean = "Pacific ocean"
        ),
        Resorts.Mountain(
            name = "Cortina-d'Ampezzo",
            country = "Italy",
            photo = R.drawable.cortina,
            mountain = "Alps"
        ),
        Resorts.Ocean(
            name = "Seychelles islands",
            country = "Republic of Seychelles",
            photo = R.drawable.seychelles,
            ocean = "Indian ocean"
        ),
        Resorts.Mountain(
            name = "Mont Tremblant",
            country = "Canada",
            photo = R.drawable.mont_tremblant,
            mountain = "Mont Tremblant"
        ),
        Resorts.Ocean(
            name = "Canary islands",
            country = "Spain",
            photo = R.drawable.canary,
            ocean = "Atlantic ocean"
        ),
        Resorts.Mountain(
            name = "Chamonix",
            country = "France",
            photo = R.drawable.chamonix,
            mountain = "Alps"
        ),
        Resorts.Sea(
            name = "Ibiza",
            country = "Spain",
            photo = R.drawable.ibiza,
            sea = "Mediterranean sea"
        ),
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFab.setOnClickListener {
            isChecked = true
            addResort()
        }
        initResortsList()
        if (savedInstanceState != null) {
            resortsList = savedInstanceState.getParcelableArrayList<Resorts>(KEY_FOR_LIST)!!
            isChecked = savedInstanceState.getBoolean(KEY_FOR_CHECK)
        }
        resortsAdapter?.updateResorts(resortsList)
        resortsAdapter?.notifyDataSetChanged()
        if (isChecked) {
            addResort()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_FOR_LIST, resortsList)
        outState.putBoolean(KEY_FOR_CHECK, isChecked)
    }

    //  очищаем адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        resortsAdapter = null
    }

    //  инициализация списка
    private fun initResortsList() {
        resortsAdapter = ResortsAdapter { position -> deleteResort(position) }
        with(resortsListRV) {
            adapter = resortsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    //  добавление нового элемента
    private fun addResort() {
        lateinit var newResort: Resorts
        var errorType = false
        val view = (view as ViewGroup).inflate(R.layout.add_new_resort)
        AlertDialog.Builder(requireContext())
            .setTitle("Add new resort")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                if (isNotEmptyFields(view)) {
                    isChecked = false
                    when (view.addTypeResortEditText.text.toString()) {
                        "Mountain" -> newResort = Resorts.Mountain(
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            R.drawable.chamonix,
                            view.addPlaceEditText.text.toString()
                        )
                        "Sea" -> newResort = Resorts.Sea(
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            R.drawable.ibiza,
                            view.addPlaceEditText.text.toString()
                        )
                        "Ocean" -> newResort = Resorts.Ocean(
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            R.drawable.seychelles,
                            view.addPlaceEditText.text.toString()
                        )
                        else -> errorType = true

                    }
//                     обработка некорректного типа курорта
                    if (errorType) {
                        Toast.makeText(
                            requireContext(),
                            "Incorrect resort type entered. *Please read the note",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        resortsList.add(0, newResort)
                        resortsAdapter?.updateResorts(resortsList)
                        resortsAdapter?.notifyItemInserted(0)
                        resortsListRV.scrollToPosition(0)
                        if (resortsList.isNotEmpty()) {
                            emptyResortsList.isVisible = false
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "The form is incomplete, please, try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> isChecked = false }
            .create()
            .show()
    }

    //  удаление элемента
    private fun deleteResort(position: Int) {
        resortsList =
            resortsList.filterIndexed { index, _ -> index != position } as ArrayList<Resorts>
        resortsAdapter?.updateResorts(resortsList)
        resortsAdapter?.notifyItemRemoved(position)
        if (resortsList.isEmpty()) {
            emptyResortsList.isVisible = true
        }
    }

    //    проверка заполненности полей в диалоге
    private fun isNotEmptyFields(view: View): Boolean {
        return (view.addTypeResortEditText.text.isNotEmpty() && view.addNameResortEditText.text.isNotEmpty()
                && view.addPlaceEditText.text.isNotEmpty() && view.addCountryEditText.text.isNotEmpty())
    }


    //  ключи для передаваемого списка и статуса FAB
    companion object {
        private const val KEY_FOR_CHECK = "keyForCheck"
        private const val KEY_FOR_LIST = "keyForList"
    }
}
