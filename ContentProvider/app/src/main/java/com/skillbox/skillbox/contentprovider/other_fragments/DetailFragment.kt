package com.skillbox.skillbox.contentprovider.other_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.databinding.DetailFragmentBinding
import com.skillbox.skillbox.contentprovider.toast
import kotlinx.android.synthetic.main.view_toolbar.*


class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

//    получаем вью модель
    private val viewModel: DetailInfoViewModel by viewModels()

//    получаем аргументы, переданные из пред фрагмента
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        инициализируем стартовый экран
        initContactStartScreen()
//        подписываемся на обновления вью модели
        bindViewModel()
//        удаление контакта по кнопке удаления
        binding.deleteContactActionButton.setOnClickListener {
            deleteContact()
        }
    }

//    инициализация стартового экрана
    private fun initContactStartScreen() {
//    инициализируем тулбар
        toolbar.title = "Contact information"
//    получаем данные контакта
        viewModel.getContactData(args.contact.id, args.contact.name)
    }

    private fun bindViewModel() {
//        подписываемся на лайв дату загрузки данных контакта и устанавливаем их на стартовый экран
        viewModel.contact.observe(viewLifecycleOwner) { contact ->
            binding.nameOfContactDetailTextView.text = contact.name
            binding.phonesOfContactDetailTextView.text = contact.phones.joinToString("\n")
            binding.emailsOfContactDetailTextView.text = contact.eMails.joinToString("\n")
        }

//        выбрасываем тост и переходим на предвдущий экран в случае успешного удаления контакта
        viewModel.deleting.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                toast(R.string.contact_was_deleted)
                findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
            }
        }

//        активируем лоадэр во время загрузки
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//        в случае ошибки выбрасываем тост с ошибкой
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

//    удаление контакта
    private fun deleteContact() {
        viewModel.deleteContactFromMemory(args.contact.id)
    }
}