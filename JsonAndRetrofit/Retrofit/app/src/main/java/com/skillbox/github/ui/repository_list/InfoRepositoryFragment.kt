package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.skillbox.github.databinding.InfoRepoFragmentBinding

class InfoRepositoryFragment : Fragment() {
    private var _binding: InfoRepoFragmentBinding? = null
    private val binding get() = _binding!!

    private val checkStatusViewModel: InfoRepoViewModel by viewModels()

    private val args: InfoRepositoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InfoRepoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.infoRepoButton.setOnClickListener {
            checkIsStarredStatus()
        }
        observer()
    }

    private fun checkIsStarredStatus() {
        val nameRepo = args.name
        val nameOwner = args.owner.name
        checkStatusViewModel.getStatus(nameRepo, nameOwner)
    }

    private fun observer() {
        checkStatusViewModel.infoRepo.observe(viewLifecycleOwner) { status ->
            binding.infoRepoTextView.text = status
        }
        checkStatusViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), checkStatusViewModel.getError, Toast.LENGTH_SHORT).show()
        }
    }

}