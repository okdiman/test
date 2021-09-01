package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
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
import kotlinx.android.synthetic.main.clubs_list.view.*


class TournamentDetailsFragment : Fragment() {
    private var _binding: TournamentDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private var clubsListAdapter: ClubAdapter? = null

    private var clubsListAdapterForAdding: ClubAdapter? = null

    private val args: TournamentDetailsFragmentArgs by navArgs()

    private val tournamentViewModel: TournamentDetailsViewModel by viewModels()

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
        view?.let {
            Glide.with(it)
                .load(args.tournament.cupPicture.toUri())
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(binding.detailCupPictureImageView)
        }
        binding.detailTypeOfTournamentTextView.text = "Type: ${args.tournament.type.toString()}"
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
        val view = (view as ViewGroup).inflate(R.layout.clubs_list)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add club to tournament")
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .show()

        clubsListAdapterForAdding = ClubAdapter { club ->
            val tournament = args.tournament.copy(clubsCount = args.tournament.clubsCount?.plus(1))
            tournamentViewModel.updateTournament(
                tournament,
                TournamentsAndClubsCrossRef(tournament.id, club.title)
            )
            clubsListAdapter?.items = clubsListAdapter?.items?.plus(club)
            binding.detailClubsCountInTournamentTextView.text =
                "Clubs count: ${tournament.clubsCount.toString()}"
            dialog.cancel()
        }
        with(view.clubsListForTournRV) {
            adapter = clubsListAdapterForAdding
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        clubsListAdapterForAdding?.items = clubs
    }

    private fun bindingViewModel() {

        tournamentViewModel.deleteTournamentLD.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(TournamentDetailsFragmentDirections.actionTournamentDetailsFragmentToTournamentsFragment())
            }
        }

        tournamentViewModel.getAllClubs.observe(viewLifecycleOwner) { clubsList ->
            addClubToTournament(clubsList)
        }

        tournamentViewModel.tournamentWithClubsLiveData.observe(viewLifecycleOwner) {
            clubsListAdapter?.items = it.clubs
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