package com.skillbox.skillbox.contentprovider.detailfragment

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.databinding.AddContactFragmentBinding
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

    private fun initToolbar() {
        toolbar.title = "Добавление контакта"
    }

    private fun addNewContact() {
        viewModel.addNewContact(
            binding.nameEditText.text.toString(),
            binding.phoneEditText.toString(),
            binding.emailEditText.toString()
        )
    }

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

    private fun bindViewModel() {
        viewModel.gettingOfNewContact.observe(viewLifecycleOwner) { add ->
            if (add) {
                toast(R.string.adding_new_contact)
                findNavController().navigate(R.id.action_addContactFragment_to_mainFragment)
            }
        }
    }

    private fun onShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.on_never_ask_again_for_permission)
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_list_permission_denied)
    }
}