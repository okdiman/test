package com.skillbox.skillbox.contentprovider.main

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.adapter.ContactListAdapter
import com.skillbox.skillbox.contentprovider.databinding.ContactFragmentBinding
import com.skillbox.skillbox.contentprovider.toast
import kotlinx.android.synthetic.main.view_toolbar.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class ContactsListFragment : Fragment() {
    private var _binding: ContactFragmentBinding? = null
    private val binding get() = _binding!!

    //    создаем ViewModel и Adapter
    private val viewModel: ContactsListFragmentViewModel by viewModels()
    private var contactsAdapter: ContactListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    обнуляем баиндинг и адаптер при закрытии фрагмента
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        contactsAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        инициализируем тулбар
        initToolbar()
//        запрашиваем разрешения на чтение списка контактов
        requestPermission()
//        инициализиурем список контактов
        initList()
//        подписываемся на обновления ViewModel
        bindViewModel()
//        переходим на экран добавления контакта по клику на кнопку добавления
        binding.addContactActionButton.setOnClickListener {
            findNavController().navigate(ContactsListFragmentDirections.actionContactsListFragmentToAddContactFragment())
        }
    }

    //    инициализаиия тулбара
    private fun initToolbar() {
        toolbar.title = "List of contacts"
    }

    //    запрос разрешений на чтение списка контактов
    private fun requestPermission() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onShowRationale,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                onPermissionDenied = ::onContactPermissionDenied,
                requiresPermission = { viewModel.getAllContacts() }
            )
                .launch()
        }
    }

    //    инициализация списка контактов
    private fun initList() {
//    инициализация адаптера
        contactsAdapter = ContactListAdapter { contact ->
//            передаем объект контакта в фрагмент детальной информации
            val action =
                ContactsListFragmentDirections.actionContactsListFragmentToDetailFragment(contact)
//            переходим во фрагмент детальной информации при клике на контакт
            findNavController().navigate(action)
        }
//    инициализируем список контактов
        with(binding.contactsRecyclerView) {
            adapter = contactsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    //    ответ в случае, если пользователь отказал и поставил галочку "больше не спрашивать
    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.on_never_ask_again_for_permission)
    }

    //    ответ в случае отказа пользователя
    private fun onContactPermissionDenied() {
        toast(R.string.contact_list_permission_denied)
    }

    //    баиндим ViewModel
    private fun bindViewModel() {
        viewModel.contactList.observe(viewLifecycleOwner) { listOfContacts ->
//           передаем полученный список контактов в адаптер
            contactsAdapter?.items = listOfContacts
        }

//        следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//        выбрасываем тост с ошибкой в случае ошибки
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

}