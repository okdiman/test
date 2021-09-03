package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.adapters.TournamentAdapter
import com.skillbox.skillbox.roomdao.database.TypeOfTournament
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.databinding.TournamentFragmentBinding
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.utils.toast
import kotlinx.android.synthetic.main.new_tournament_item.view.*

class TournamentsFragment : Fragment() {
    private var _binding: TournamentFragmentBinding? = null
    private val binding get() = _binding!!
    private val tournamentViewModel: TournamentsViewModel by viewModels()
    private var tournamentAdapter: TournamentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TournamentFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tournamentAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        устанавливаем лисенер для кнопки добавляения турнира
        binding.addTournamentButton.setOnClickListener {
            addNewTournament()
        }
//        устанавливаем лисенер для кнопки удаления всех турниров
        binding.deleteAllTournamentsButton.setOnClickListener {
            tournamentViewModel.deleteAllTournaments()
        }
//        инициализируем стартовый экран
        init()
//        подписываемся на обновления ViewModel
        bindingViewModel()
    }

    //    инициализация стартового экрана
    private fun init() {
//        по клику на элемент списка переходим на экран детальной информации о турнире
        tournamentAdapter = TournamentAdapter { tournament ->
            val action =
                TournamentsFragmentDirections.actionTournamentsFragmentToTournamentDetailsFragment(
                    tournament
                )
            findNavController().getBackStackEntry(R.id.tournamentsFragment)
            findNavController().navigate(action)
        }
        with(binding.tournamentsListRV) {
            adapter = tournamentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
//        получаем список всех турниров
        tournamentViewModel.getAllTournaments()
    }

    //    добавление нового турнира
    private fun addNewTournament() {
//        создание списка для выбора пользователем типа турнира
        val types = listOf(
            "Championship",
            "Cup"
        )
//        инфлейтим вьюшку добавления нового турнира
        val view = (view as ViewGroup).inflate(R.layout.new_tournament_item)
//        создание адаптера для спиннера
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            types
        )
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        view.typeOfNewTournamentSpinner.adapter = spinnerAdapter
//        вызываем диалог добавления нового турнира
        AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("Add new tournament")
            .setPositiveButton("Ok") { _, _ ->
//                проверяем корректность заполения полей пользователем
                if (view.titleOfNewTournamentEditText.text.toString()
                        .isNotEmpty()
                ) {
//                    создаем объект турнира
                    val tournament = Tournaments(
                        0,
                        view.titleOfNewTournamentEditText.text.toString(),
                        when (view.typeOfNewTournamentSpinner.selectedItem.toString()) {
                            "Championship" -> {
                                TypeOfTournament.CHAMPIONSHIP
                            }
                            "Cup" -> {
                                TypeOfTournament.CUP
                            }
                            else -> {
                                TypeOfTournament.UNKNOWN
                            }
                        },
                        view.prizeMoneyOfNewTournamentEditText.text.toString().toIntOrNull(),
                        view.imageOfNewTournamentEditText.text.toString()
                    )
//                    добавляем турнир
                    tournamentViewModel.addNewTournament(tournament)
                } else {
//                    в случае некорректного заполнения пользователем формы, выдаем тост
                    toast(R.string.incorrect_form)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    //    подписка на обновление viewModel
    private fun bindingViewModel() {
//           передаем полученный список контактов в адаптер
        tournamentViewModel.tournamentsList.observe(viewLifecycleOwner) { tournamentList ->
            tournamentAdapter?.items = tournamentList
        }

//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        tournamentViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//        выбрасываем тост с ошибкой в случае ошибки
        tournamentViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}