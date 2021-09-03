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
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Stadiums
import com.skillbox.skillbox.roomdao.databinding.ClubsDetailsFragmentBinding
import com.skillbox.skillbox.roomdao.utils.glideLoadImage
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.utils.toast
import kotlinx.android.synthetic.main.new_stadium.view.*

class ClubsDetailsFragment : Fragment() {
    private var _binding: ClubsDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    //    получаем аргументы, переданные на вход из предыдущего фрагмента
    private val args: ClubsDetailsFragmentArgs by navArgs()
    private val detailClubViewModel: ClubsDetailsViewModel by viewModels()

    //    создаем список стадионов с полем добавления нового
    private var stadiumsList = mutableListOf(
        "Add new"
    )

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
//        инициализируем стартовый экран
        initStartScreen()
//        устанавливаем лисенер на кнопку удаления клуба
        binding.deleteClubButton.setOnClickListener {
            detailClubViewModel.deleteClub(args.club)
        }
//        подписываемся на обновления ViewModel
        bindingViewModel()
//        устанавливаем лисенер на названия стадиона для перехода на экран детальной информации о стадионе
        binding.stadiumNameOfClubDetailTextView.setOnClickListener {
//            переход на экран детальной информации о стадионе
            findNavController().navigate(
                ClubsDetailsFragmentDirections.actionClubsDetailsFragmentToStadiumDetailsFragment(
//                    передаем во фрагмент детальной инфы о стадионе название стадиона
                    binding.stadiumNameOfClubDetailTextView.text.toString()
                )
            )
        }
    }

    //    инициализация стартового экрана
    @SuppressLint("SetTextI18n")
    private fun initStartScreen() {
//    заполняем все поля view для переданного нам клуба
        binding.clubImageView.glideLoadImage(args.club.emblem.toUri())
        binding.cityOfClubDetailTextView.text = "City: ${args.club.city}"
        binding.countryOfClubDetailTextView.text = "Country: ${args.club.country}"
        binding.titleOfClubDetailTextView.text = "Title: ${args.club.title}"
        if (args.club.yearOfFoundation != null) {
            binding.yearOfFoundationOfClubDetailTextView.text =
                "Year of foundation: ${args.club.yearOfFoundation}"
        } else {
            binding.yearOfFoundationOfClubDetailTextView.text =
                "Year of foundation not specified"
        }
//        получаем объект клуба с турнирами для вывода на экран списка турниров, в которых участвует клуб
        detailClubViewModel.getClubWithTournaments(args.club.title, args.club.city)
//    если поле стадиона у клуба не заполнено, инициализируем поле спиннера вместо заполнения поля стадиона
        if (args.club.stadium_id != null) {
//            получаем стадион по id
            detailClubViewModel.getStadiumById(args.club.stadium_id!!.toLong())
        } else {
            initSpinner()
        }
    }

    //    инициализация спиннера для выбора стадиона пользователем или добавления нового
    private fun initSpinner() {
//        создаем адаптер для спиннера
        val spinnerAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                stadiumsList
            )
//        устнавливаем статусы view при активном спиннере
        statusSpinnerActive()
//        получаем список всех стадионов
        detailClubViewModel.getAllStadiums()
//        устанавливаем адаптер для спиннера
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.spinnerOfClubDetail.adapter = spinnerAdapter
//        обрабатываем клик на кнопку добавления стадиона
        binding.addStadiumClubButton.setOnClickListener {
            when (binding.spinnerOfClubDetail.selectedItem.toString()) {
//                если пользователь выбирает добавление нового стадиона, то вызываем соответствующий диалог
                "Add new" -> {
//                    инфлейтим вьюшку добавления стадиона
                    val view = (view as ViewGroup).inflate(R.layout.new_stadium)
//                    вызываем диалог
                    AlertDialog.Builder(requireContext())
                        .setTitle("Add new Stadium")
                        .setView(view)
                        .setPositiveButton("Ok") { _, _ ->
//                            проверяем заполненностей обязательных полей
                            if (view.nameOfNewStadiumET.text.isNotEmpty() &&
                                view.capacityOfNewStadiumET.text.isNotEmpty()
                            ) {
//                                создаем объект стадиона, используя введенные пользователем данные
                                val stadium = Stadiums(
                                    0,
                                    view.nameOfNewStadiumET.text.toString(),
                                    view.photoOfNewStadiumET.text.toString(),
                                    view.capacityOfNewStadiumET.text.toString().toInt(),
                                    view.yearOfBuildOfNewStadiumET.text.toString().toIntOrNull()
                                )
//                                добавляем стадион в БД
                                detailClubViewModel.addNewStadium(listOf(stadium))
                            } else {
//                                выводим тост в случае неправильного заполенения полей пользователем
                                toast(R.string.incorrect_form)
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
                }
//                если пользователь выбирает один из имеющихся стадионов, то добавляем его в соотвествующее поле клуба
                else -> detailClubViewModel.getStadiumByName(binding.spinnerOfClubDetail.selectedItem.toString())
            }
        }
    }

    //    подписываемся на обновления ViewModel
    @SuppressLint("SetTextI18n")
    private fun bindingViewModel() {
//    описываем действия в случае удаления клуба
        detailClubViewModel.deleteClub.observe(viewLifecycleOwner) { deleted ->
//            если клуб успешно удален, то переходим в предыдущий фрагмент
            if (deleted) {
                findNavController().popBackStack()
            }
        }

//    следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        detailClubViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//    выбрасываем тост с ошибкой в случае ошибки
        detailClubViewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

//    действия при получении объекта стадиона
        detailClubViewModel.getStadium.observe(viewLifecycleOwner) { stadium ->
//            устанавливаем статус вьюшек при неактивном спиннере
            statusSpinnerInactive()
//            устанавиваем название стадиона в соотвествующее поле
            binding.stadiumNameOfClubDetailTextView.text = stadium.stadiumName
//            если у клуба стадион был не установлен, то устанавливаем стадион в БД
            if (args.club.stadium_id == null) {
//                создаем объект клуба
                val clubForUpdate = Clubs(
                    stadium.id,
                    args.club.title,
                    args.club.city,
                    args.club.country,
                    args.club.emblem,
                    args.club.yearOfFoundation
                )
//                обновляем данные БД
                detailClubViewModel.updateClub(clubForUpdate)
            }
        }

//        добавляем все полученные стадионы в наш список
        detailClubViewModel.getAllStadiums.observe(viewLifecycleOwner) { listOfStadiums ->
            listOfStadiums.forEach {
                stadiumsList.add(it.stadiumName)
            }
        }

//        инициализируем поле списка турниров для клуба
        detailClubViewModel.clubsWithTournaments.observe(viewLifecycleOwner) { clubWithTournaments ->
            val list = mutableListOf<String>()
            clubWithTournaments.tournaments.forEach {
                list.add(it.title)
            }
            if (list.isNotEmpty()) {
                binding.tournamentsOfClubDetailTextView.text =
                    "Tournaments in which the club participates: ${list.joinToString(", ")}"
            } else {
                binding.tournamentsOfClubDetailTextView.text =
                    "The club has not yet been registered in any tournament"
            }
        }
    }

    //    статус вью при активном спиннере
    private fun statusSpinnerActive() {
        binding.stadiumNameOfClubDetailTextView.isVisible = false
        binding.spinnerOfClubDetail.isVisible = true
        binding.addStadiumClubButton.isVisible = true
        binding.addingStadiumInfoDetailTextView.isVisible = true
        binding.stadiumInfoDetailTextView.isVisible = false
    }

    //    статус вью при неактивном спиннере
    private fun statusSpinnerInactive() {
        binding.stadiumInfoDetailTextView.isVisible = true
        binding.addingStadiumInfoDetailTextView.isVisible = false
        binding.stadiumNameOfClubDetailTextView.isVisible = true
        binding.spinnerOfClubDetail.isVisible = false
        binding.addStadiumClubButton.isVisible = false
    }

}