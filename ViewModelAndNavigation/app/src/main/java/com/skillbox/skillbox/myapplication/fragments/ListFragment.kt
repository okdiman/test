package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.ResortListViewModel
import com.skillbox.skillbox.myapplication.adapters.resorts.ResortsAdapter
import com.skillbox.skillbox.myapplication.databinding.ListFragmentBinding
import com.skillbox.skillbox.myapplication.inflate
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.add_new_resort.view.*


class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!
    private var resortsAdapter: ResortsAdapter? = null

    private val resortListViewModel: ResortListViewModel by viewModels()

    private val listenerForLongClick: (Int) -> Unit = { position ->
        deleteResort(position)
    }

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
            addResort()
        }
        initResortsList()
        //      уточняем наличие обновлений списка
        observeViewModelState()
    }


    //  очищаем адаптер при удалении вьюшки
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        resortsAdapter = null
    }

    //  инициализация списка
    private fun initResortsList() {
//      подписываемся на обновление ViewModel
        resortListViewModel.resorts
            .observe(viewLifecycleOwner) {
                resortsAdapter = ResortsAdapter(listenerForLongClick) { id ->
                    val action = ListFragmentDirections.actionListFragment3ToDetailsFragment(id)
                    findNavController().navigate(action)
                }
                with(binding.resortsListRV) {
                    adapter = resortsAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                    itemAnimator = LandingAnimator()
                }
            }
        if (resortListViewModel.resorts.value.isNullOrEmpty()) {
            binding.emptyResortsList.isVisible = true
        }
    }

    //  добавление нового элемента
    private fun addResort() {
        val view = (view as ViewGroup).inflate(R.layout.add_new_resort)
        AlertDialog.Builder(requireContext())
            .setTitle("Add new resort")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                if (isNotEmptyFields(view)) {
                    view.selectTypesOfResort.selectedItem.toString()
                    resortListViewModel.addResort(
                        requireContext(),
                        view.selectTypesOfResort.selectedItem.toString(),
                        view.addNameResortEditText.text.toString(),
                        view.addCountryEditText.text.toString(),
                        view.addPhotoEditText.text.toString(),
                        view.addPlaceEditText.text.toString()
                    )
                    binding.emptyResortsList.isVisible = false
                    binding.resortsListRV.scrollToPosition(0)
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
    }

    //  удаление элемента
    private fun deleteResort(position: Int) {
        resortListViewModel.deleteResort(requireContext(),position)
    }

    //   подписываемся на обновление ViewModel
    private fun observeViewModelState() {
        resortListViewModel.resorts.observe(viewLifecycleOwner) { newResorts ->
            resortsAdapter?.items = newResorts
            Log.d("ewq", "$newResorts")
            if (newResorts.isEmpty()) {
                binding.emptyResortsList.isVisible = true
            }

        }
        resortListViewModel.showToast
            .observe(viewLifecycleOwner) {

            }
    }

    //    проверка заполненности полей в диалоге
    private fun isNotEmptyFields(view: View): Boolean {
        return (view.addNameResortEditText.text.isNotEmpty()
                && view.addPlaceEditText.text.isNotEmpty()
                && view.addCountryEditText.text.isNotEmpty()
                && view.addPhotoEditText.text.isNotEmpty())
    }
}