package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.adapters.ClubAdapter
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef
import com.skillbox.skillbox.roomdao.databinding.TournamentDetailsFragmentBinding
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.utils.toast
import kotlinx.android.synthetic.main.clubs_list.view.*


class TournamentDetailsFragment : Fragment() {
    private var _binding: TournamentDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    //    создаем 2 адаптера: для списка клубов у турнира и списка клубов
    //    при добавлении нового клуба в турнир
    private var clubsListAdapter: ClubAdapter? = null
    private var clubsListAdapterForAdding: ClubAdapter? = null

    private val args: TournamentDetailsFragmentArgs by navArgs()

    //    создаем нуллабльный объект спомогательной таблицы для последующей работы
    //    с ним в нескольких участках кода и возможности проверить на нулл
    private var tableTournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef? = null

    private val tournamentViewModel: TournamentDetailsViewModel by viewModels()

    //    создаем нуллабльный объект турнира для последующей работы с ним в нескольких участках кода
    //    и возможности проверить на нулл
    private var tournament: Tournaments? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TournamentDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        clubsListAdapter = null
        clubsListAdapterForAdding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        инициализируем стартовый экран
        initStartScreen()
//        подписываемся на обновления ViewModel
        bindingViewModel()
    }

    //    инициализация стартового экрана
    @SuppressLint("SetTextI18n")
    private fun initStartScreen() {
//        заполяняем все вьюшки в соотвествии с полученными данными турнира
        view?.let {
            Glide.with(it)
                .load(args.tournament.cupPicture.toUri())
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(binding.detailCupPictureImageView)
        }
        binding.detailTypeOfTournamentTextView.text = "Type: ${args.tournament.type}"
        binding.detailTitleOfTournamentTextView.text = "Title: ${args.tournament.title}"
        binding.detailPrizeMoneyOfTournamentTextView.text =
            "Prize money: ${args.tournament.prizeMoney.toString()}"
//        устанавливаем лисенер на кнопку добавления нового клуба в турнир
        binding.addClubToTournamentButton.setOnClickListener {
            tournamentViewModel.getAllClubs()
        }
//        устанавливаем лисенер на удаление турнира
        binding.detailDeleteTournamentButton.setOnClickListener {
            tournamentViewModel.deleteTournament(args.tournament)
        }
//        инициализируем список клубов для турнира
        initList()
    }

    //    инициализация списка клубов-участников турнира
    private fun initList() {
        clubsListAdapter = ClubAdapter { club ->
//            по клику переходим в окно детальной информации о клубе
            val action =
                TournamentDetailsFragmentDirections.actionTournamentDetailsFragmentToClubsDetailsFragment(
                    club
                )
            findNavController().navigate(action)
        }
        with(binding.detailTournamentRV) {
            adapter = clubsListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
//        получаем список турниров вместе с клубами-учатсниками
        tournamentViewModel.getTournamentWithClubs(args.tournament.id)
    }

    //    добавление клуба в турнир
    @SuppressLint("SetTextI18n")
    private fun addClubToTournament(clubs: List<Clubs>) {
//        получаем вспомогательную таблицу турнир/клубы
        tournamentViewModel.gettingCrossTableForTournament(args.tournament.id)
//        инфлейтим вьюшку добавления клуба в турнир
        val viewDialog = (view as ViewGroup).inflate(R.layout.clubs_list)
//        создаем объект диалога для последующего закрытия
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add club to tournament")
            .setView(viewDialog)
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
//            если турнир уже был изменен, то вызываем его вспомогательную таблицу
        if (tournament != null) {
            tournamentViewModel.gettingCrossTableForTournament(tournament!!.id)
        }
//        создаем адаптер для списка-клубов для выбора
        clubsListAdapterForAdding = ClubAdapter { club ->
//            создаем список названий клубов-учатсников
            val listOfClubsTitles = mutableListOf<String>()
//            заполяем список названий клубов-участников
            tableTournamentsAndClubsCrossRef?.clubTitle?.forEach { title ->
                listOfClubsTitles.add(title.toString())
            }
            Log.i("clubsCount", listOfClubsTitles.toString())
//                если клуба в списке еще нет, то добавляем его
            if (!listOfClubsTitles.contains(club.title)) {
//                если турнир еще не был изменен, то устанавливаем его, как полученный из предыдущего фрагмента
                if (tournament == null) {
                    tournament = args.tournament
                }
//                создаем объект вспомогательной таблицы
                val tournamentsAndClubsCrossRef =
                    TournamentsAndClubsCrossRef(tournament!!.id, club.title)
//                обновляем турнир в БД
                tournamentViewModel.updateTournament(
                    tournament!!,
                    tournamentsAndClubsCrossRef
                )
//                получаем турнир вместе со списком клубов-участников
                tournamentViewModel.getTournamentWithClubs(tournament!!.id)
            } else {
//                если клуб уже есть в списке, то показываем соотсвествующий тост
                toast(R.string.selected_club_error)
            }
//            закрываем диалог
            dialog.dismiss()
        }
//        инициализируем recyclerView
        with(viewDialog.clubsListForTournRV) {
            adapter = clubsListAdapterForAdding
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
//        обновляем список клубов-участников
        clubsListAdapterForAdding?.items = clubs
    }

    //    подписка на обновления ViewModel
    @SuppressLint("SetTextI18n")
    private fun bindingViewModel() {
//        при удалении турнира переходим на предыдущий экран
        tournamentViewModel.deleteTournamentLD.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().previousBackStackEntry
            }
        }
//        при обновлениии турнира показываем тост
        tournamentViewModel.updateTournament.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Tournament was updated", Toast.LENGTH_SHORT)
                    .show()
            }
        }
//        после получения всех клубов передаем список в диалог выбора клуба для добавления
        tournamentViewModel.getAllClubs.observe(viewLifecycleOwner) { clubsList ->
            addClubToTournament(clubsList)
        }
//        при получении списка клубов-участников турнира, обновляем список
        tournamentViewModel.tournamentWithClubs.observe(viewLifecycleOwner) {
            Log.i("tournamentWithClubs", it.toString())
            clubsListAdapter?.items = it.clubs
        }
//        устанавливаем значение вспомогательной таблицы
        tournamentViewModel.gettingCrossTableForTournament.observe(viewLifecycleOwner) { table ->
            tableTournamentsAndClubsCrossRef = table
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