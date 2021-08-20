package com.skillbox.skillbox.contentprovideraddapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.skillbox.contentprovider.toast
import com.skillbox.skillbox.contentprovideraddapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    обнуляем баиндинг и адаптер при закрытии фрагмента
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveNewUserButton.setOnClickListener {
            saveUser(
                binding.enterUserNameEditText.text.toString(),
                binding.enterAgeNameEditText.text.toString().toInt()
            )
        }
        binding.getAllUsersButton.setOnClickListener {
            getAllUsers()
        }
        bindViewModel()
    }

    private fun saveUser(name: String, age: Int) {
        viewModel.addNewContact(name, age)
    }

    private fun getAllUsers() {
        viewModel.getAllContacts()
    }

    //    баиндим ViewModel
    private fun bindViewModel() {
        viewModel.courseList.observe(viewLifecycleOwner) { listOfContacts ->
//           передаем полученный список контактов в адаптер
            Toast.makeText(
                requireContext(),
                "в списке пользователей: $listOfContacts",
                Toast.LENGTH_SHORT
            ).show()
            binding.usersList.text = listOfContacts.toString()
        }
//        выбрасываем тост с ошибкой в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }
}