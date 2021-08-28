package com.skillbox.skillbox.roomdao.fragments.clubs

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.skillbox.skillbox.contentprovider.toast
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.databinding.ClubsDetailsFragmentBinding

class ClubsDetailsFragment : Fragment() {
    private var _binding: ClubsDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ClubsDetailsFragmentArgs by navArgs()

    private val detailClubViewModel: ClubsDetailsViewModel by viewModels()

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
    }

    private fun bindingViewModel() {
        detailClubViewModel.deleteClub.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                findNavController().navigate(ClubsDetailsFragmentDirections.actionClubsDetailsFragmentToClubsFragment())
            } else {
                toast(R.string.error)
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
    }
}