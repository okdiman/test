package com.skillbox.skillbox.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            navigateToResortsList()
        }

        binding.ImagesHorizontalListButton.setOnClickListener {
            navigateToImageHorizontalList()
        }

        binding.ImagesGridListButton.setOnClickListener {
            navigateToImageGridList()
        }

        binding.ImagesStaggeredGridListButton.setOnClickListener {
            navigateToStaggeredGridList()
        }

        binding.ImagesCoverFlowListButton.setOnClickListener {
            navigateToCoverFlowList()
        }
    }

    //    чистим баиндинг
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //    функции навигации на различные экраны
    private fun navigateToResortsList() {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.mainConteiner, ListFragment())
            .addToBackStack("MainMenu")
            .commit()
    }

    private fun navigateToImageHorizontalList() {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.mainConteiner, ImageHorizontalListFragment())
            .addToBackStack("MainMenu")
            .commit()
    }

    private fun navigateToImageGridList() {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.mainConteiner, ImagesGridLayoutFragment())
            .addToBackStack("MainMenu")
            .commit()
    }

    private fun navigateToStaggeredGridList() {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.mainConteiner, ImagesStaggeredGridLayoutFragment())
            .addToBackStack("MainMenu")
            .commit()
    }

    private fun navigateToCoverFlowList() {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.mainConteiner, ImagesCoverFlowListFragment())
            .addToBackStack("MainMenu")
            .commit()
    }
}