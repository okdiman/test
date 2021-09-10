package com.skillbox.skillbox.scopedstorage.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skillbox.skillbox.scopedstorage.R
import com.skillbox.skillbox.scopedstorage.databinding.AddNewVideoBinding
import com.skillbox.skillbox.scopedstorage.utils.isConnected
import com.skillbox.skillbox.scopedstorage.utils.toast

class AddDialogFragment : BottomSheetDialogFragment() {
    private var _binding: AddNewVideoBinding? = null
    private val binding get() = _binding!!

    private val addDialogViewModel: AddDialogFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNewVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            saveNewVideo()
        }
        bindingViewModel()
    }

    private fun saveNewVideo() {
        if (requireContext().isConnected) {
            if (binding.titleTextField.editText!!.text.toString().isNotEmpty() &&
                binding.uriTextField.editText!!.text.toString().isNotEmpty()
            ) {
                val isValidUrl =
                    Patterns.WEB_URL.matcher(binding.uriTextField.editText!!.text.toString())
                        .matches()
                if (isValidUrl) {
                    addDialogViewModel.downloadVideo(
                        binding.titleTextField.editText!!.text.toString(),
                        binding.uriTextField.editText!!.text.toString()
                    )
                } else {
                    toast(R.string.incorrect_url)
                }
            } else {
                toast(R.string.incorrect_form)
            }
        } else {
            toast(R.string.internet_is_not_available)
        }

    }

    private fun bindingViewModel() {
        addDialogViewModel.videoDownloaded.observe(viewLifecycleOwner) { successful ->
            if (successful) {
                toast(R.string.successful_download)
            } else {
                toast(R.string.unsuccessful_download)
            }
            dismiss()
        }

//    следим за статусом загрузки и взависимости от этого меняем статус вьюшек
        addDialogViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

//    выбрасываем тост с ошибкой в случае ошибки
        addDialogViewModel.isError.observe(viewLifecycleOwner) { error ->
            toast(error)
        }
    }
}