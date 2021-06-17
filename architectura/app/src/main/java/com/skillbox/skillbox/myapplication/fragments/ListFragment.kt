package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.myapplication.ResortListViewModel
import com.skillbox.skillbox.myapplication.adapters.resorts.ResortsAdapter
import com.skillbox.skillbox.myapplication.classes.Resorts
import com.skillbox.skillbox.myapplication.databinding.ListFragmentBinding
import jp.wasabeef.recyclerview.animators.LandingAnimator


class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!
    private var resortsAdapter: ResortsAdapter? = null

    private val resortListViewModel: ResortListViewModel by viewModels()

    private var resortsList = arrayListOf<Resorts>()
    private var isChecked: Boolean = false
//    private var resortsList = arrayListOf<Resorts>(
//        Resorts.Mountain(
//            name = "Aspen, Colorado",
//            country = "USA",
//            photo = R.drawable.aspen,
//            mountain = "Aspen"
//        ),
//        Resorts.Ocean(
//            name = "Hawaiian Islands",
//            country = "USA",
//            photo = R.drawable.hawaii,
//            ocean = "Pacific ocean"
//        ),
//        Resorts.Mountain(
//            name = "Cortina-d'Ampezzo",
//            country = "Italy",
//            photo = R.drawable.cortina,
//            mountain = "Alps"
//        ),
//        Resorts.Ocean(
//            name = "Seychelles islands",
//            country = "Republic of Seychelles",
//            photo = R.drawable.seychelles,
//            ocean = "Indian ocean"
//        ),
//        Resorts.Mountain(
//            name = "Mont Tremblant",
//            country = "Canada",
//            photo = R.drawable.mont_tremblant,
//            mountain = "Mont Tremblant"
//        ),
//        Resorts.Ocean(
//            name = "Canary islands",
//            country = "Spain",
//            photo = R.drawable.canary,
//            ocean = "Atlantic ocean"
//        ),
//        Resorts.Mountain(
//            name = "Chamonix",
//            country = "France",
//            photo = R.drawable.chamonix,
//            mountain = "Alps"
//        ),
//        Resorts.Sea(
//            name = "Ibiza",
//            country = "Spain",
//            photo = R.drawable.ibiza,
//            sea = "Mediterranean sea"
//        ),
//    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addFab.setOnClickListener {
            isChecked = true
            addResort()
        }
        initResortsList()
        if (savedInstanceState != null) {
            resortsList = savedInstanceState.getParcelableArrayList(KEY_FOR_LIST)!!
            isChecked = savedInstanceState.getBoolean(KEY_FOR_CHECK)
        }
        resortsAdapter?.items = resortsList
        if (isChecked) {
            addResort()
        }
        if (resortsList.isEmpty()) {
            binding.emptyResortsList.isVisible = true
        }
        updateResortsList()
    }

    //  кладем данные в бандл
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_FOR_LIST, resortsList)
        outState.putBoolean(KEY_FOR_CHECK, isChecked)
    }

    //  очищаем адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        resortsAdapter = null
    }

    //  инициализация списка
    private fun initResortsList() {
        resortsAdapter = ResortsAdapter { position -> deleteResort(position) }
        with(binding.resortsListRV) {
            adapter = resortsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = LandingAnimator()
        }
    }

    //  добавление нового элемента
    private fun addResort() {
        view?.let { context?.let { context -> resortListViewModel.addResort(it, context) } }
        updateResortsList()
    }

    private fun deleteResort(position: Int) {
        resortListViewModel.deleteResort(position)
        updateResortsList()
        if (resortsList.isEmpty()) {
            binding.emptyResortsList.isVisible = true
        }
    }

    private fun updateResortsList() {
        resortListViewModel.getResortList()
    }


    //  ключи для передаваемого списка и статуса FAB
    companion object {
        private const val KEY_FOR_CHECK = "keyForCheck"
        private const val KEY_FOR_LIST = "keyForList"
    }
}