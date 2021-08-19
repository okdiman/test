package com.skillbox.skillbox.contentprovider.other_fragments

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.databinding.AddContactFragmentBinding
import com.skillbox.skillbox.contentprovider.phonePattern
import com.skillbox.skillbox.contentprovider.toast
import kotlinx.android.synthetic.main.view_toolbar.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class AddContactFragment : Fragment() {
    private var _binding: AddContactFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddContactFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addContactButton.setOnClickListener {
            requestPermission()
        }
        initToolbar()
        bindViewModel()
    }

    //    инициализируем Тулбар
    private fun initToolbar() {
        toolbar.title = "Add contact"
    }

    //    добавляем новый контакст
    private fun addNewContact() {
//        проверяем заполненность полей
        if (binding.nameEditText.text.toString().isEmpty() || binding.phoneEditText.text.toString()
                .isEmpty()
        ) {
            return toast(R.string.fill_the_form)
        }
//        проверяем корректность введенного номера и e-mail
        if (phonePattern.matcher(binding.phoneEditText.text.toString()).matches().not()) {
            return toast(R.string.incorrect_number)
        }
        if (binding.emailEditText.text.toString().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                binding.emailEditText.text.toString()
            ).matches().not()
        ) {
            return toast(R.string.incorrect_email)
        }
//        добавляем контакт
        viewModel.addNewContact(
            binding.nameEditText.text.toString(),
            binding.phoneEditText.text.toString(),
            binding.emailEditText.text.toString()
        )
    }

    //    запрос разрешения на запись нового контакта и выполнение записи
    private fun requestPermission() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.WRITE_CONTACTS,
                onShowRationale = ::onShowRationale,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                onPermissionDenied = ::onContactPermissionDenied,
                requiresPermission = ::addNewContact
            )
                .launch()
        }
    }

    //    баиндим ViewModel
    private fun bindViewModel() {
//        следим за успешностью сохранения контакта
        viewModel.gettingOfNewContact.observe(viewLifecycleOwner) { add ->
            if (add) {
//               в случае успешного добавления выводим тост и возвращаемся на экран списка контактов
                toast(R.string.adding_new_contact)
                findNavController().navigate(AddContactFragmentDirections.actionAddContactFragmentToContactsListFragment())
            }
        }
//        выбрасываем тост с ошибкой в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                isLoading()
            } else {
                isIdle()
            }
        }
    }

    //    состояние загрузки
    private fun isLoading() {
        binding.addContactButton.isEnabled = false
        binding.emailEditText.isEnabled = false
        binding.nameEditText.isEnabled = false
        binding.phoneEditText.isEnabled = false
        binding.progressBar.isEnabled = true
    }

    //    состояние обычной работы
    private fun isIdle() {
        binding.addContactButton.isEnabled = true
        binding.emailEditText.isEnabled = true
        binding.nameEditText.isEnabled = true
        binding.phoneEditText.isEnabled = true
        binding.progressBar.isEnabled = false
    }

    private fun onShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    //   ответ в случае, если пользователь отказал и поставил галочку "больше не спрашивать
    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.on_never_ask_again_for_permission)
    }

    //   ответ в случае отказа пользователя
    private fun onContactPermissionDenied() {
        toast(R.string.contact_list_permission_denied)
    }
}