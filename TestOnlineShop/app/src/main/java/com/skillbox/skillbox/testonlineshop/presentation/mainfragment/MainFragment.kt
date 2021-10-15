package com.skillbox.skillbox.testonlineshop.presentation.mainfragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.data.models.TypesOfProducts
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding
import com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment.MainFragmentViewPagerAdapter


class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.filterImageView.setOnClickListener {
            createBottomSheetDialogFragment()
        }
    }

    //    инициализация tabLayout и viewPager
    private fun init() {
//    список иконок для tabLayout
        val icons = listOf(
            R.drawable.ic_smartphone,
            R.drawable.ic_computer,
            R.drawable.ic_games,
            R.drawable.ic_book,
            R.drawable.ic_gifts
        )
        binding.mainViewPager.adapter =
            MainFragmentViewPagerAdapter(TypesOfProducts.values().toList(), this)
        TabLayoutMediator(binding.categoryTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = TypesOfProducts.values().toList()[position].toString()
            tab.setIcon(icons[position])
        }.attach()
    }

    //    создание BottomSheetDialogFragment дял фильтрации
    private fun createBottomSheetDialogFragment() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToFilterBottomSheetDialog())
    }
}