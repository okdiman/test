package com.skillbox.skillbox.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.add_new_resort.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment() : Fragment(R.layout.list_fragment) {

    private var resortsAdapter: ResortsAdapter? = null

    //    private var resortsList = emptyList<Resorts>()
    private var resortsList = listOf<Resorts>(
        Resorts.Mountains(
            name = "Aspen, Colorado",
            country = "USA",
            photo = R.drawable.aspen,
            mountain = "Aspen"
        ),
        Resorts.Oceans(
            name = "Hawaiian Islands",
            country = "USA",
            photo = R.drawable.hawaii,
            ocean = "Pacific ocean"
        ),
        Resorts.Mountains(
            name = "Cortina-d'Ampezzo",
            country = "Italy",
            photo = R.drawable.cortina,
            mountain = "Alps"
        ),
        Resorts.Oceans(
            name = "Seychelles islands",
            country = "Republic of Seychelles",
            photo = R.drawable.seychelles,
            ocean = "Indian ocean"
        ),
        Resorts.Mountains(
            name = "Mont Tremblant",
            country = "Canada",
            photo = R.drawable.mont_tremblant,
            mountain = "Mont Tremblant"
        ),
        Resorts.Oceans(
            name = "Canary islands",
            country = "Spain",
            photo = R.drawable.canary,
            ocean = "Atlantic ocean"
        ),
        Resorts.Mountains(
            name = "Chamonix",
            country = "France",
            photo = R.drawable.chamonix,
            mountain = "Alps"
        ),
        Resorts.Seas(
            name = "Ibiza",
            country = "Spain",
            photo = R.drawable.ibiza,
            sea = "Mediterranean sea"
        ),
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFab.setOnClickListener {
            addResort()
        }
        if (savedInstanceState != null) {

        } else {
            initResortsList()
            resortsAdapter?.updateResorts(resortsList)
            resortsAdapter?.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.put
    }

    //  очищаем адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        resortsAdapter = null
    }

    //    инициализация списка
    private fun initResortsList() {
        resortsAdapter = ResortsAdapter { position -> deleteResort(position) }
        with(resortsListRV) {
            adapter = resortsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    //добавление нового элемента
    private fun addResort() {
        AlertDialog.Builder(requireContext())
            .setTitle("Add new resort")
            .setView(R.layout.add_new_resort)
            .setPositiveButton("Add") { _, _ ->
                Log.d("tag", "${addTypeResortEditText?.text}")
                val newResort = when (addTypeResortEditText?.text.toString()) {
                    "mountain" -> Resorts.Mountains(
                        addNameResortEditText.text.toString(),
                        addCountryEditText.text.toString(),
                        R.drawable.chamonix,
                        addTypeResortEditText.text.toString()
                    )
                    "sea" -> Resorts.Seas(
                        addNameResortEditText.text.toString(),
                        addCountryEditText.text.toString(),
                        R.drawable.ibiza,
                        addTypeResortEditText.text.toString()
                    )
                    "ocean" -> Resorts.Oceans(
                        addNameResortEditText.text.toString(),
                        addCountryEditText.text.toString(),
                        R.drawable.seychelles,
                        addTypeResortEditText.text.toString()
                    )
                    else -> error("Incorrect type of resort")
                }
                resortsList = listOf(newResort) + resortsList
                resortsAdapter?.updateResorts(resortsList)
                resortsAdapter?.notifyItemInserted(0)
                resortsListRV.scrollToPosition(0)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
        if (!resortsList.isEmpty()) {
            emptyResortsList.isVisible = false
        }
    }

    //  удаление элемента
    private fun deleteResort(position: Int) {
        resortsList = resortsList.filterIndexed { index, _ -> index != position }
        resortsAdapter?.updateResorts(resortsList)
        resortsAdapter?.notifyItemRemoved(position)
        if (resortsList.isEmpty()) {
            emptyResortsList.isVisible = true
        }
    }
}