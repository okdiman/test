package com.skillbox.skillbox.roomdao.fragments.stadiums

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.databinding.StadiumDetailsFragmentBinding


class StadiumDetailsFragment : Fragment() {
    private var _binding: StadiumDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val stadiumViewModel: StadiumDetailsViewModel by viewModels()

    private val args: StadiumDetailsFragmentArgs by navArgs()

    lateinit var stadium: Stadiums

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StadiumDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("stadium", args.stadiumName)
        stadiumViewModel.getStadiumWithAttendance(args.stadiumName)
        bindingViewModel()
        binding.deleteStadiumButton.setOnClickListener {
            deleteStadium(stadium)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init(stadium: Stadiums) {
        Log.i("stadium", stadium.toString())
        binding.titleOfStadiumTextView.text = "Stadium name: ${stadium.stadiumName}"
        binding.yearOfBuildOfStadiumTextView.text =
            "Year of construction ${stadium.yearOfBuild.toString()}"
        binding.capacityStadiumTextView.text = "Capacity: ${stadium.capacity.toString()}"
        view?.let {
            Glide.with(it)
                .load(stadium.stadiumPicture?.toUri())
                .error(R.drawable.ic_sync_problem)
                .placeholder(R.drawable.ic_cloud_download)
                .into(binding.stadiumImageView)
        }
        if (stadium.averageAttendanceOfStadium != null) {
            binding.averageAttendanceOfStadiumTextView.text =
                stadium.averageAttendanceOfStadium.toString()
        } else {

        }
    }

    private fun deleteStadium(stadium: Stadiums) {
        stadiumViewModel.deleteStadium(stadium)
    }

    private fun bindingViewModel() {

//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        stadiumViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

        stadiumViewModel.success.observe(viewLifecycleOwner) { success ->
            if (success){
                findNavController().previousBackStackEntry
            }
        }

//        выбрасываем тост с ошибкой в случае ошибки
        stadiumViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        stadiumViewModel.getStadium.observe(viewLifecycleOwner) { gettedStadium ->
            stadium = gettedStadium
            init(stadium)
        }

    }
}