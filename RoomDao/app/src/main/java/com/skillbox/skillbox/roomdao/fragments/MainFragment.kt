package com.skillbox.skillbox.roomdao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skillbox.skillbox.roomdao.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startClubsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToClubsFragment())
        }
        binding.startTournamentsButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToTournamentsFragment())
        }
    }
}