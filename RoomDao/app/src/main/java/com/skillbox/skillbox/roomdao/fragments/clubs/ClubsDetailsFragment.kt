package com.skillbox.skillbox.roomdao.fragments.clubs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.skillbox.skillbox.contentprovider.inflate
import com.skillbox.skillbox.contentprovider.toast
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.databinding.ClubsDetailsFragmentBinding
import kotlinx.android.synthetic.main.new_stadium.view.*

class ClubsDetailsFragment : Fragment() {
    private var _binding: ClubsDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ClubsDetailsFragmentArgs by navArgs()

    private val detailClubViewModel: ClubsDetailsViewModel by viewModels()

    private var stadiumsList = mutableListOf(
        "Add new"
    )

    private val spinnerAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            stadiumsList
        )
    }

    private lateinit var stadium: Stadiums

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ClubsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.deleteClubButton.setOnClickListener {
            detailClubViewModel.deleteClub(args.club)
        }
        bindingViewModel()
        binding.stadiumNameOfClubDetailTextView.setOnClickListener {
            findNavController().navigate(
                ClubsDetailsFragmentDirections.actionClubsDetailsFragmentToStadiumDetailsFragment(
                    binding.stadiumNameOfClubDetailTextView.text.toString()
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.cityOfClubDetailTextView.text = "City: ${args.club.city}"
        view?.let {
            Glide.with(it)
                .load(args.club.emblem?.toUri())
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(binding.clubImageView)
        }
        binding.countryOfClubDetailTextView.text = "Country: ${args.club.country}"
        binding.titleOfClubDetailTextView.text = "Title: ${args.club.title}"
        binding.yearOfFoundationOfClubDetailTextView.text =
            "Year of foundation: ${args.club.yearOfFoundation}"
        if (args.club.stadium_id != null) {
            getStadiumById(args.club.stadium_id!!.toLong())
        } else {
            initSpinner()
        }
    }

    private fun getStadiumById(stadiumId: Long) {
        detailClubViewModel.getStadiumById(stadiumId)
    }

    private fun initSpinner() {
        detailClubViewModel.getAllStadiums()
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.spinnerOfClubDetail.adapter = spinnerAdapter
        binding.stadiumNameOfClubDetailTextView.isVisible = false
        binding.spinnerOfClubDetail.isVisible = true
        binding.addStadiumClubButton.isVisible = true
        binding.addingStadiumInfoDetailTextView.isVisible = true
        binding.stadiumInfoDetailTextView.isVisible = false
        binding.addStadiumClubButton.setOnClickListener {
            when (binding.spinnerOfClubDetail.selectedItem.toString()) {
                "Add new" -> {
                    val view = (view as ViewGroup).inflate(R.layout.new_stadium)
                    AlertDialog.Builder(requireContext())
                        .setTitle("Add new Stadium")
                        .setView(view)
                        .setPositiveButton("Ok") { _, _ ->
                            if (view.nameOfNewStadiumET.text.isNotEmpty() &&
                                view.capacityOfNewStadiumET.text.isNotEmpty()
                            ) {
                                stadium = Stadiums(
                                    0,
                                    view.nameOfNewStadiumET.text.toString(),
                                    view.photoOfNewStadiumET.text.toString(),
                                    view.capacityOfNewStadiumET.text.toString().toInt(),
                                    view.yearOfBuildOfNewStadiumET.text.toString().toIntOrNull(),
                                    null
                                )
                                detailClubViewModel.addNewStadium(listOf(stadium))
                            } else {
                                toast(R.string.incorrect_form)
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
                }
                else -> detailClubViewModel.getStadiumByName(binding.spinnerOfClubDetail.selectedItem.toString())
            }
        }
    }

    private fun bindingViewModel() {
        detailClubViewModel.deleteClub.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                findNavController().navigate(ClubsDetailsFragmentDirections.actionClubsDetailsFragmentToClubsFragment())
            } else {
                toast(R.string.error)
            }
        }

        detailClubViewModel.success.observe(viewLifecycleOwner) { added ->
            if (added) {

            }
        }

//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        detailClubViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//        выбрасываем тост с ошибкой в случае ошибки
        detailClubViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        detailClubViewModel.getStadium.observe(viewLifecycleOwner) { stadium ->
            binding.stadiumInfoDetailTextView.isVisible = true
            binding.addingStadiumInfoDetailTextView.isVisible = false
            binding.stadiumNameOfClubDetailTextView.isVisible = true
            binding.spinnerOfClubDetail.isVisible = false
            binding.addStadiumClubButton.isVisible = false
            binding.stadiumNameOfClubDetailTextView.text = stadium.stadiumName
            if (args.club.stadium_id == null) {
                val clubForUpdate = Clubs(
                    stadium.id,
                    args.club.title,
                    args.club.city,
                    args.club.country,
                    args.club.emblem,
                    args.club.yearOfFoundation
                )
                detailClubViewModel.updateClub(clubForUpdate)
            }
        }

        detailClubViewModel.getAllStadiums.observe(viewLifecycleOwner) { listOfStadiums ->
            listOfStadiums.forEach {
                stadiumsList.add(it.stadiumName)
            }
        }
    }
}