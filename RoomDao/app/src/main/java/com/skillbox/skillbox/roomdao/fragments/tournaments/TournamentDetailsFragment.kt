package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.roomdao.adapters.ClubAdapter
import com.skillbox.skillbox.roomdao.databinding.TournamentDetailsFragmentBinding
import com.skillbox.skillbox.roomdao.fragments.clubs.ClubsFragmentDirections


class TournamentDetailsFragment: Fragment() {
    private var _binding: TournamentDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private var clubsListAdapter: ClubAdapter? = null

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        clubsListAdapter = ClubAdapter { club ->
            val action =
                ClubsFragmentDirections.actionClubsFragmentToClubsDetailsFragment(
                    club
                )
            findNavController().navigate(action)
        }
        with(binding.detailTournamentRV) {
            adapter = clubsListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }
}