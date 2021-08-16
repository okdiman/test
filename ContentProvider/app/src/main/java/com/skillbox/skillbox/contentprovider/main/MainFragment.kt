package com.skillbox.skillbox.contentprovider.main

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.contentprovider.R
import com.skillbox.skillbox.contentprovider.adapter.ContactListAdapter
import com.skillbox.skillbox.contentprovider.databinding.MainFragmentBinding
import com.skillbox.skillbox.contentprovider.toast
import kotlinx.android.synthetic.main.view_toolbar.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainFragmentViewModel by viewModels()
    private var contactsAdapter: ContactListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        contactsAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        requestPermission()
        initList()
        bindViewModel()
    }

    private fun initToolbar() {
        toolbar.title = "Список контактов"
    }

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

    private fun initList() {
        contactsAdapter = ContactListAdapter { }
        with(binding.contactsRecyclerView) {
            adapter = contactsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
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

    private fun bindViewModel() {
        viewModel.contactList.observe(viewLifecycleOwner) { listOfContacts ->
            contactsAdapter?.items = listOfContacts
        }
    }

}