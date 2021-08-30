package com.skillbox.skillbox.roomdao.fragments.clubs

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.utils.toast
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.adapters.ClubAdapter
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.databinding.ClubsFragmentBinding
import kotlinx.android.synthetic.main.new_club_item.view.*


class ClubsFragment : Fragment() {
    private var _binding: ClubsFragmentBinding? = null
    private val binding get() = _binding!!

    private val clubsViewModel: ClubsViewModel by viewModels()

    private var clubsListAdapter: ClubAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ClubsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        clubsListAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewClubButton.setOnClickListener {
            addNewClub()
        }
        binding.deleteAllTClubsButton.setOnClickListener {
            deleteAllClubs()
        }
        initList()
        bindingViewModel()
    }

    private fun initList() {
        clubsListAdapter = ClubAdapter { club ->
            val action =
                ClubsFragmentDirections.actionClubsFragmentToClubsDetailsFragment(
                    club
                )
            findNavController().navigate(action)
        }
        with(binding.clubsListRV) {
            adapter = clubsListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        clubsViewModel.getAllClubs()
    }

    private fun addNewClub() {
        val view = (view as ViewGroup).inflate(R.layout.new_club_item)
        AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("Add new club")
            .setPositiveButton("Ok") { _, _ ->
                if (view.titleOfNewClubEditText.text.toString()
                        .isNotEmpty() && view.cityOfNewClubEditText.text.toString()
                        .isNotEmpty() && view.countryOfNewClubEditText.text.toString().isNotEmpty()
                ) {
                    val club = Clubs(
                        null,
                        view.titleOfNewClubEditText.text.toString(),
                        view.cityOfNewClubEditText.text.toString(),
                        view.countryOfNewClubEditText.text.toString(),
                        view.emblemOfNewClubEditText?.text.toString(),
                        view.yearOfFoundationOfNewClubEditText.text.toString().toIntOrNull()
                    )
                    clubsViewModel.addNewClub(club)
                } else {
                    toast(R.string.incorrect_form)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }


    private fun deleteAllClubs() {
        clubsViewModel.deleteAllClubs()
    }

    private fun bindingViewModel() {
        clubsViewModel.clubsList.observe(viewLifecycleOwner) { clubsList ->
//           передаем полученный список контактов в адаптер
            clubsListAdapter?.items = clubsList
        }

//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        clubsViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//        выбрасываем тост с ошибкой в случае ошибки
        clubsViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

}