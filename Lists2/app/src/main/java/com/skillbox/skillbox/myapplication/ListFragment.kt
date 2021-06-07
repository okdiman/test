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
import com.skillbox.skillbox.myapplication.adapters.ResortsAdapter
import com.skillbox.skillbox.myapplication.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.add_new_resort.view.*
import kotlinx.android.synthetic.main.list_fragment.*
import kotlin.random.Random

class ListFragment() : Fragment(R.layout.list_fragment) {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private var resortsAdapter: ResortsAdapter? = null
    var isChecked: Boolean = false

//    private var resortsList = arrayListOf<Resorts>()
    private var resortsList = arrayListOf<Resorts>(
        Resorts.Mountain(
            id =1,
            name = "Aspen, Colorado",
            country = "USA",
            photo = R.drawable.aspen,
            mountain = "Aspen"
        ),
        Resorts.Ocean(
            id =2,
            name = "Hawaiian Islands",
            country = "USA",
            photo = R.drawable.hawaii,
            ocean = "Pacific ocean"
        ),
        Resorts.Mountain(
            id =3,
            name = "Cortina-d'Ampezzo",
            country = "Italy",
            photo = R.drawable.cortina,
            mountain = "Alps"
        ),
        Resorts.Ocean(
            id =4,
            name = "Seychelles islands",
            country = "Republic of Seychelles",
            photo = R.drawable.seychelles,
            ocean = "Indian ocean"
        ),
        Resorts.Mountain(
            id =5,
            name = "Mont Tremblant",
            country = "Canada",
            photo = R.drawable.mont_tremblant,
            mountain = "Mont Tremblant"
        ),
        Resorts.Ocean(
            id =6,
            name = "Canary islands",
            country = "Spain",
            photo = R.drawable.canary,
            ocean = "Atlantic ocean"
        ),
        Resorts.Mountain(
            id =7,
            name = "Chamonix",
            country = "France",
            photo = R.drawable.chamonix,
            mountain = "Alps"
        ),
        Resorts.Sea(
            id =8,
            name = "Ibiza",
            country = "Spain",
            photo = R.drawable.ibiza,
            sea = "Mediterranean sea"
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        if (isChecked) {
            addResort()
        }
        if (resortsList.isEmpty()) {
            emptyResortsList.isVisible = true
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
            addItemDecoration(ItemOffSetDecoration(requireContext()))
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
                            Random.nextLong(),
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            mountainsPhoto(),
                            view.addPlaceEditText.text.toString()
                        )
                        "Sea" -> newResort = Resorts.Sea(
                            Random.nextLong(),
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            seasPhoto(),
                            view.addPlaceEditText.text.toString()
                        )
                        "Ocean" -> newResort = Resorts.Ocean(
                            Random.nextLong(),
                            view.addNameResortEditText.text.toString(),
                            view.addCountryEditText.text.toString(),
                            oceansPhoto(),
                            view.addPlaceEditText.text.toString()
                        )
                    }
                    resortsList.add(0, newResort)
                    resortsAdapter?.updateResorts(resortsList)
                    resortsListRV.scrollToPosition(0)
                    if (resortsList.isNotEmpty()) {
                        emptyResortsList.isVisible = false
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
        if (resortsList.isEmpty()) {
            emptyResortsList.isVisible = true
        }
    }

    //    проверка заполненности полей в диалоге
    private fun isNotEmptyFields(view: View): Boolean {
        return (view.addNameResortEditText.text.isNotEmpty()
                && view.addPlaceEditText.text.isNotEmpty()
                && view.addCountryEditText.text.isNotEmpty())
    }

    //    так как не работают библиотеки добавляем фото рандомно из имеющихся в памяти
    private fun mountainsPhoto(): Int {
        val listOfMountainsPhoto = listOf<Int>(
            R.drawable.chamonix,
            R.drawable.aspen,
            R.drawable.mont_tremblant,
            R.drawable.cortina
        )
        return listOfMountainsPhoto.random()
    }

    private fun oceansPhoto(): Int {
        val listOfOceansPhoto = listOf<Int>(
            R.drawable.canary,
            R.drawable.hawaii,
            R.drawable.seychelles
        )
        return listOfOceansPhoto.random()
    }

    private fun seasPhoto(): Int {
        val listOfOceansPhoto = listOf<Int>(
            R.drawable.greek_sea,
            R.drawable.red_sea,
            R.drawable.ibiza
        )
        return listOfOceansPhoto.random()
    }


    //  ключи для передаваемого списка и статуса FAB
    companion object {
        private const val KEY_FOR_CHECK = "keyForCheck"
        private const val KEY_FOR_LIST = "keyForList"
    }
}
