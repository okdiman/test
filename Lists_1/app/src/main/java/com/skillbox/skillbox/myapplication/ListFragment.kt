package com.skillbox.skillbox.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.myapplication.databinding.AddNewResortBinding
import com.skillbox.skillbox.myapplication.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.add_new_resort.view.*

class ListFragment() : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private var resortsAdapter: ResortsAdapter? = null
    var isChecked: Boolean = false

    private var resortsList = arrayListOf<Resorts>()
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
            resortsList = savedInstanceState.getParcelableArrayList<Resorts>(KEY_FOR_LIST)!!
            isChecked = savedInstanceState.getBoolean(KEY_FOR_CHECK)
        }
        resortsAdapter?.updateResorts(resortsList)
        resortsAdapter?.notifyDataSetChanged()
        if (isChecked) {
            addResort()
        }
        if (resortsList.isEmpty()) {
            binding.emptyResortsList.isVisible = true
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
        }
    }

    //  добавление нового элемента
    private fun addResort() {
        lateinit var newResort: Resorts
        val view = (view as ViewGroup).inflate(R.layout.add_new_resort)

        AlertDialog.Builder(requireContext())
            .setTitle("Add new resort")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                if (isNotEmptyFields(view)) {
                    isChecked = false
                    when (view.selectTypesOfResort.selectedItem.toString()) {
                        "Mountain" -> newResort = Resorts.Mountain(
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            view.addPhotoEditText.text.toString(),
                            view.addPlaceEditText.text.toString()
                        )
                        "Sea" -> newResort = Resorts.Sea(
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            view.addPhotoEditText.text.toString(),
                            view.addPlaceEditText.text.toString()
                        )
                        "Ocean" -> newResort = Resorts.Ocean(
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            view.addPhotoEditText.text.toString(),
                            view.addPlaceEditText.text.toString()
                        )
                    }
                    resortsList = resortsList.clone() as ArrayList<Resorts>
                    resortsList.add(0, newResort)
                    resortsAdapter?.updateResorts(resortsList)
                    resortsAdapter?.notifyItemInserted(0)
                    binding.resortsListRV.scrollToPosition(0)
                    if (resortsList.isNotEmpty()) {
                        binding.emptyResortsList.isVisible = false
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
            binding.emptyResortsList.isVisible = true
        }
    }

    //    проверка заполненности полей в диалоге
    private fun isNotEmptyFields(view: View): Boolean {
        return (view.addNameResortEditText.text.isNotEmpty()
                && view.addPlaceEditText.text.isNotEmpty()
                && view.addCountryEditText.text.isNotEmpty()
                && view.addPhotoEditText.text.isNotEmpty())
    }


    //  ключи для передаваемого списка и статуса FAB
    companion object {
        private const val KEY_FOR_CHECK = "keyForCheck"
        private const val KEY_FOR_LIST = "keyForList"
    }
}
