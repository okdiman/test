package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skillbox.skillbox.myapplication.R
import com.skillbox.skillbox.myapplication.activity.MainActivity
import com.skillbox.skillbox.myapplication.databinding.MainFragmentBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resortsListNavigationButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listFragment)
        }

        binding.ImagesHorizontalListButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_imageHorizontalListFragment)
        }

        binding.ImagesGridListButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_imagesGridLayoutFragment)
        }

        binding.ImagesStaggeredGridListButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_imagesStaggeredGridLayoutFragment)
        }

        binding.ImagesCoverFlowListButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_imagesCoverFlowListFragment)
        }
    }

    //    чистим баиндинг
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}