package com.skillbox.skillbox.testonlineshop.presentation.mainfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding
import com.skillbox.skillbox.testonlineshop.domain.models.TypesOfProducts
import com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment.MainFragmentViewPagerAdapter
import com.skillbox.skillbox.testonlineshop.presentation.mainfragment.viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val mainViewModel by viewModels<MainScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingViewModel()
        init()
        binding.run {
            filterImageView.setOnClickListener {
                createBottomSheetDialogFragment()
            }
            bottomAppBar.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.cartItemBottomBar -> findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
                }
                true
            }
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
        mainViewModel.getCartData()
    }

    //    создание BottomSheetDialogFragment дял фильтрации
    private fun createBottomSheetDialogFragment() {
        findNavController().navigate(
            MainFragmentDirections
                .actionMainFragmentToFilterBottomSheetDialog()
        )
    }

    //    подписка на изменения вью модели
    private fun bindingViewModel() {
        mainViewModel.cartLiveData.observe(viewLifecycleOwner) { result ->
            binding.bottomAppBar.getOrCreateBadge(R.id.cartItemBottomBar).run {
                isVisible = true
                number = result.basket.size
            }
        }
    }
}