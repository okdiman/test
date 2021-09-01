package com.skillbox.skillbox.roomdao.fragments.stadiums

import android.annotation.SuppressLint
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
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.databinding.StadiumDetailsFragmentBinding


class StadiumDetailsFragment : Fragment() {
    private var _binding: StadiumDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val stadiumViewModel: StadiumDetailsViewModel by viewModels()
    private val args: StadiumDetailsFragmentArgs by navArgs()

    //    создаем late init объект стадиона для использования в разных частях кода
    private lateinit var stadium: Stadiums

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
//        получаем объект стадиона вместе с посещаемостью
        stadiumViewModel.getStadiumWithAttendance(args.stadiumName)
//        подписываемся на обновления viewModel
        bindingViewModel()
//        устанавливаем лисенер на кнопку удаления стадиона
        binding.deleteStadiumButton.setOnClickListener {
            stadiumViewModel.deleteStadium(stadium)
        }
    }

    //    инициализируем стартовый экран
    @SuppressLint("SetTextI18n")
    private fun init(stadium: Stadiums) {
        Log.i("stadium", stadium.toString())
//    заполняем все поля экрана в соотсвествии с полями переданного нам стадиона
        binding.titleOfStadiumTextView.text = "Stadium name: ${stadium.stadiumName}"
        binding.yearOfBuildOfStadiumTextView.text =
            "Year of construction ${stadium.yearOfBuild.toString()}"
        binding.capacityStadiumTextView.text = "Capacity: ${stadium.capacity}"
//        if (stadium.stadiumPicture != null) {
//            view?.let {
//                Glide.with(it)
//                    .load(stadium.stadiumPicture.toUri())
//                    .error(R.drawable.ic_sync_problem)
//                    .placeholder(R.drawable.ic_cloud_download)
//                    .into(binding.stadiumImageView)
//            }
//        }

        if (stadium.averageAttendanceOfStadium != null) {
            binding.averageAttendanceOfStadiumTextView.text =
                stadium.averageAttendanceOfStadium.toString()
        }
    }

    //    подписка на обновления ViewModel
    private fun bindingViewModel() {
//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        stadiumViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//        при удалении стадиона возвращаемся на предудыщий экран
        stadiumViewModel.delete.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().previousBackStackEntry
            }
        }

//        выбрасываем тост с ошибкой в случае ошибки
        stadiumViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

//        получаем стадион и передаем его функции инициализации экрана
        stadiumViewModel.getStadium.observe(viewLifecycleOwner) { gotStadium ->
            stadium = gotStadium.stadium
            init(stadium)
        }
    }
}