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
import com.skillbox.skillbox.roomdao.R
import com.skillbox.skillbox.roomdao.adapters.ClubAdapter
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.databinding.ClubsFragmentBinding
import com.skillbox.skillbox.roomdao.utils.inflate
import com.skillbox.skillbox.roomdao.utils.toast
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
//        устанавливаем лисенер для кнопки добавления нового клуба
        binding.addNewClubButton.setOnClickListener {
            addNewClub()
        }
//        устанавливаем лисенер для кнопки удаления всех клубов
        binding.deleteAllTClubsButton.setOnClickListener {
            clubsViewModel.deleteAllClubs()
        }
//        инициализируем список клубов
        initList()
//        подписываемся на обновления ViewModel
        bindingViewModel()
    }

    //    инициализация списка клубов
    private fun initList() {
        clubsListAdapter = ClubAdapter { club ->
//            по клику на элемент списка переходим на экран детальной информации о выбранном клубе
            val action =
                ClubsFragmentDirections.actionClubsFragmentToClubsDetailsFragment(
                    club
                )
//            добавляем в getBackStackEntry фрагмент
            findNavController().getBackStackEntry(R.id.clubsFragment)
            findNavController().navigate(action)
        }
        with(binding.clubsListRV) {
            adapter = clubsListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
//    получаем список всех клубов
        clubsViewModel.getAllClubs()
    }

    //    добавление нового клуба
    private fun addNewClub() {
//    инфлейтим вьюшку добавления клуба
        val view = (view as ViewGroup).inflate(R.layout.new_club_item)
        AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle("Add new club")
            .setPositiveButton("Ok") { _, _ ->
//                проверяем корректность заполнения пользователем минимальных данных о новом клубе
                if (view.titleOfNewClubEditText.text.toString()
                        .isNotEmpty() && view.cityOfNewClubEditText.text.toString()
                        .isNotEmpty() && view.countryOfNewClubEditText.text.toString().isNotEmpty()
                ) {
//                    создаем объект клуба
                    val club = Clubs(
                        null,
                        view.titleOfNewClubEditText.text.toString(),
                        view.cityOfNewClubEditText.text.toString(),
                        view.countryOfNewClubEditText.text.toString(),
                        view.emblemOfNewClubEditText?.text.toString(),
                        view.yearOfFoundationOfNewClubEditText.text.toString().toIntOrNull()
                    )
//                    добавляем клуб в БД
                    clubsViewModel.addNewClub(club)
                } else {
//                    в случае некорректного заполнения полей, выводим тост
                    toast(R.string.incorrect_form)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    //    подписка на обновления viewModel
    private fun bindingViewModel() {
//        передаем полученный список контактов в адаптер
        clubsViewModel.clubsList.observe(viewLifecycleOwner) { clubsList ->
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