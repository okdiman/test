package com.skillbox.skillbox.testonlineshop.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.adapters.ViewPagerAdapter
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)

    private val types = listOf("Phones", "Computers", "Games", "Books", "Accessories")

    private val icons = listOf(
        R.drawable.ic_smartphone,
        R.drawable.ic_baseline_computer_24,
        R.drawable.ic_baseline_videogame_asset_24,
        R.drawable.ic_baseline_menu_book_24,
        R.drawable.ic_baseline_headset_24
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.filterImageView.setOnClickListener {

        }
    }

    private fun init() {
        binding.mainViewPager.adapter = ViewPagerAdapter(types, this)
        TabLayoutMediator(binding.categoryTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = types[position]
            tab.setIcon(icons[position])
        }.attach()
    }
}