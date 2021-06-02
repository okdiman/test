package com.skillbox.skillbox.myapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
        initResortsList()
        addFab.setOnClickListener {
            addResort()
        }
        resortsAdapter?.updateResorts(resortsList.shuffled())
        resortsAdapter?.notifyDataSetChanged()
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
            .setTitle("Add new object")
            .setView(R.layout.item_mountain)
    }

    //  удаление элемента
    private fun deleteResort(position: Int) {
        resortsList = resortsList.filterIndexed { index, resorts -> index != position }
        resortsAdapter?.updateResorts(resortsList)
        resortsAdapter?.notifyItemRemoved(position)
    }
}