package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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

    private var clubsListAdapter: ClubAdapter? = null

    private var clubsListAdapterForAdding: ClubAdapter? = null

    private val args: TournamentDetailsFragmentArgs by navArgs()

    private var tableTournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef? = null

    private val tournamentViewModel: TournamentDetailsViewModel by viewModels()

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
        initStartScreen()
        bindingViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun initStartScreen() {
//        view?.let {
//            Glide.with(it)
//                .load(args.tournament.cupPicture.toUri())
//                .error(R.drawable.ic_sync_problem)
//                .placeholder(R.drawable.ic_cloud_download)
//                .into(binding.detailCupPictureImageView)
//        }
        binding.detailTypeOfTournamentTextView.text = "Type: ${args.tournament.type}"
        binding.detailTitleOfTournamentTextView.text = "Title: ${args.tournament.title}"
        binding.detailPrizeMoneyOfTournamentTextView.text =
            "Prize money: ${args.tournament.prizeMoney.toString()}"
        binding.detailClubsCountInTournamentTextView.text =
            "Clubs count: ${args.tournament.clubsCount.toString()}"
        binding.addClubToTournamentButton.setOnClickListener {
            tournamentViewModel.getAllClubs()
        }
        binding.detailDeleteTournamentButton.setOnClickListener {
            deleteTournament(args.tournament)
        }
        initList()
    }

    private fun initList() {
        clubsListAdapter = ClubAdapter { club ->
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
        getTournamentWithClubs(args.tournament.id)
    }

    private fun getTournamentWithClubs(tournamentId: Long) {
        tournamentViewModel.getTournamentWithClubs(tournamentId)
    }

    private fun deleteTournament(tournament: Tournaments) {
        tournamentViewModel.deleteTournament(tournament)
    }

    @SuppressLint("SetTextI18n")
    private fun addClubToTournament(clubs: List<Clubs>) {
        tournamentViewModel.gettingCrossTableForTournament(args.tournament.id)
        val viewDialog = (view as ViewGroup).inflate(R.layout.clubs_list)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add club to tournament")
            .setView(viewDialog)
            .setNegativeButton("Cancel") { _, _ -> }
            .show()

        var clubsCount: Int
        if (tournament == null) {
            clubsCount = args.tournament.clubsCount!!.toInt()
        } else {
            clubsCount = tournament!!.clubsCount!! + 1
            tournamentViewModel.gettingCrossTableForTournament(tournament!!.id)
        }
        Log.i("clubsCount", clubsCount.toString())



        clubsListAdapterForAdding = ClubAdapter { club ->
            val listOfClubsTitles = mutableListOf<String>()
            tableTournamentsAndClubsCrossRef?.clubTitle?.forEach { title ->
                listOfClubsTitles.add(title.toString())
            }
            Log.i("clubsCount", listOfClubsTitles.toString())
            if (!listOfClubsTitles.contains(club.title)) {
                clubsCount = if (clubsCount == 0) {
                    1
                } else {
                    clubsCount++
                }
                tournament =
                    args.tournament.copy(clubsCount = clubsCount)
                Log.i("clubsCount", clubsCount.toString())
                val tournamentsAndClubsCrossRef =
                    TournamentsAndClubsCrossRef(tournament!!.id, club.title)
                tournamentViewModel.updateTournament(
                    tournament!!,
                    tournamentsAndClubsCrossRef
                )
                getTournamentWithClubs(tournament!!.id)
            } else {
                toast(R.string.selected_club_error)
            }
            dialog.dismiss()
        }

        with(viewDialog.clubsListForTournRV) {
            adapter = clubsListAdapterForAdding
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        clubsListAdapterForAdding?.items = clubs
        tableTournamentsAndClubsCrossRef = null
    }

    @SuppressLint("SetTextI18n")
    private fun bindingViewModel() {

        tournamentViewModel.deleteTournamentLD.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().previousBackStackEntry
            }
        }

        tournamentViewModel.updateTournament.observe(viewLifecycleOwner) {
            binding.detailClubsCountInTournamentTextView.text =
                "Clubs count: ${tournament!!.clubsCount.toString()}"
        }

        tournamentViewModel.getAllClubs.observe(viewLifecycleOwner) { clubsList ->
            addClubToTournament(clubsList)
        }

        tournamentViewModel.tournamentWithClubsLiveData.observe(viewLifecycleOwner) {
            Log.i("tournamentWithClubs", it.toString())
            clubsListAdapter?.items = it.clubs
        }

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