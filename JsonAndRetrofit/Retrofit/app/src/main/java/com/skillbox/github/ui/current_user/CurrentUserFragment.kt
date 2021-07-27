package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.skillbox.github.databinding.CurrentUserFragmentBinding

class CurrentUserFragment : Fragment() {

    private var _binding: CurrentUserFragmentBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel: CurrentUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrentUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfo()
        observer()
    }

    private fun getInfo() {
        infoViewModel.getUsersInfo()
    }

    private fun observer() {
        infoViewModel.userInfo.observe(viewLifecycleOwner) { info ->
            binding.userInfoTextView.text = info
        }
        infoViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), infoViewModel.getError, Toast.LENGTH_LONG).show()
        }
        infoViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.infoProgressBar.isVisible = it
        }
    }
}