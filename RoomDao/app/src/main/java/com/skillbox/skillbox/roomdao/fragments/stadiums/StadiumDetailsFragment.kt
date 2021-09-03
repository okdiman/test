package com.skillbox.skillbox.roomdao.fragments.stadiums

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
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.connections.StadiumsWithAttendance
import com.skillbox.skillbox.roomdao.database.entities.Attendance
import com.skillbox.skillbox.roomdao.databinding.StadiumDetailsFragmentBinding
import com.skillbox.skillbox.roomdao.utils.glideLoadImage
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.utils.toast
import kotlinx.android.synthetic.main.set_attendance.view.*


class StadiumDetailsFragment : Fragment() {
    private var _binding: StadiumDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val stadiumViewModel: StadiumDetailsViewModel by viewModels()
    private val args: StadiumDetailsFragmentArgs by navArgs()

    //    создаем late init объект стадиона для использования в разных частях кода
    private lateinit var stadium: StadiumsWithAttendance

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
            stadiumViewModel.deleteStadium(stadium.stadium)
        }
//        устанавливаем лисенер для кнопки изменения посещаемости
        binding.changeAttendanceStadiumButton.setOnClickListener {
            changeAttendance()
        }
    }

    //    инициализируем стартовый экран
    @SuppressLint("SetTextI18n")
    private fun init(stadium: StadiumsWithAttendance) {
        Log.i("stadium", stadium.toString())
//    заполняем все поля экрана в соотсвествии с полями переданного нам стадиона
        binding.titleOfStadiumTextView.text = "Stadium name: ${stadium.stadium.stadiumName}"
        if (stadium.stadium.yearOfBuild != null) {
            binding.yearOfBuildOfStadiumTextView.text =
                "Year of construction ${stadium.stadium.yearOfBuild}"
        } else {
            binding.yearOfBuildOfStadiumTextView.text =
                "Year of construction not specified"
        }
        binding.capacityStadiumTextView.text = "Capacity: ${stadium.stadium.capacity}"
        binding.stadiumImageView.glideLoadImage(stadium.stadium.stadiumPicture.toUri())
        if (stadium.attendance?.averageAttendance != null) {
            binding.averageAttendanceOfStadiumTextView.text =
                "Attendance: ${stadium.attendance.averageAttendance}"
        } else {
            binding.averageAttendanceOfStadiumTextView.text = "Attendance not specified"
        }
    }

    //    изменение посещаемости стадиона
    private fun changeAttendance() {
        val view = (view as ViewGroup).inflate(R.layout.set_attendance)
        AlertDialog.Builder(requireContext())
            .setMessage("Enter attendance of stadium")
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
//                проверяем поле на заполненность, так же посещаемость не может быть больше вместимости
                if (view.newAttendanceET.text.toString()
                        .isNotEmpty() && view.newAttendanceET.text.toString()
                        .toInt() <= stadium.stadium.capacity
                ) {
//                    устанавливаем посещаеомсть
                    stadiumViewModel.changeAttendance(
                        Attendance(
                            0,
                            stadium.stadium.id,
                            (view.newAttendanceET.text.toString().toInt()),
                            0
                        )
                    )
                } else {
                    toast(R.string.incorrect_form)
                }
            }
            .show()


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

//        при обновлении стадиона обновляем данные экрана
        stadiumViewModel.update.observe(viewLifecycleOwner) { update ->
            if (update) {
//              получаем объект стадиона вместе с посещаемостью
                stadiumViewModel.getStadiumWithAttendance(args.stadiumName)
            }
        }

//        выбрасываем тост с ошибкой в случае ошибки
        stadiumViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

//        получаем стадион и передаем его функции инициализации экрана
        stadiumViewModel.getStadium.observe(viewLifecycleOwner) { gotStadium ->
            stadium = gotStadium
            init(stadium)
        }
    }
}