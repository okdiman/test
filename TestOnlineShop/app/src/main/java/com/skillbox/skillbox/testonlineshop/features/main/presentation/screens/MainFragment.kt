package com.skillbox.skillbox.testonlineshop.features.main.presentation.screens

import android.graphics.Color
import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding
import com.skillbox.skillbox.testonlineshop.features.main.domain.entities.TypesOfProducts
import com.skillbox.skillbox.testonlineshop.features.main.presentation.adapters.viewpager.MainFragmentViewPagerAdapter
import com.skillbox.skillbox.testonlineshop.features.main.presentation.screens.viewmodel.MainScreenViewModel
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
            bottomAppBar.run {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.cartItemBottomBar -> findNavController()
                            .navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
                    }
                    true
                }
//                создаем объект провайдера контура вью
//                и указываем только верхние радиусы для закругления
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        val curveRadius = 48F
                        outline?.setRoundRect(
                            0,
                            0,
                            view!!.width,
                            (view.height + curveRadius).toInt(),
                            curveRadius
                        )
                    }
                }
//                применяем изменения контура
                clipToOutline = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        устанавливаем выбранной иконку главного экрана при влзвращении на него
        binding.bottomAppBar.selectedItemId = R.id.explorerItemBottomBar
    }

    //    инициализация tabLayout и viewPager
    private fun init() {
//    список иконок для tabLayout
        val icons = listOf(
            R.drawable.phones,
            R.drawable.computers,
            R.drawable.health,
            R.drawable.books,
            R.drawable.ic_gifts,
            R.drawable.ic_sports
        )

        binding.mainViewPager.adapter =
            MainFragmentViewPagerAdapter(TypesOfProducts.values().toList(), this)
        TabLayoutMediator(binding.categoryTabLayout, binding.mainViewPager) { tab, position ->
            val view = layoutInflater.inflate(
                R.layout.tab_layout_custom_view,
                binding.categoryTabLayout as ViewGroup,
                false
            )
            view.findViewById<ImageView>(R.id.tabImageView).setImageResource(icons[position])
            view.findViewById<TextView>(R.id.tabTextView).text =
                TypesOfProducts.values().toList()[position].toString()
            tab.run {
                customView = view
                if (position == 0) {
//                изменение фона CardView работает некорректно(меняет форму на квадрат)
//                    customView?.findViewById<CardView>(R.id.tabCardView)
//                        ?.setBackgroundColor(Color.parseColor("#FF6E4E"))
                    customView?.findViewById<TextView>(R.id.tabTextView)
                        ?.setTextColor(Color.parseColor("#FF6E4E"))
                }
            }
        }.attach()
//        создаем объект лисенера для tabLayout
        val listener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                изменение фона CardView работает некорректно(меняет форму на квадрат)
//                tab?.customView?.findViewById<CardView>(R.id.tabCardView)
//                    ?.setBackgroundColor(Color.parseColor("#FF6E4E"))
                tab?.customView?.findViewById<TextView>(R.id.tabTextView)
                    ?.setTextColor(Color.parseColor("#FF6E4E"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.customView?.findViewById<CardView>(R.id.tabCardView)
//                    ?.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                tab?.customView?.findViewById<TextView>(R.id.tabTextView)
                    ?.setTextColor(Color.parseColor("#B3B3C3"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        }
        binding.categoryTabLayout.addOnTabSelectedListener(listener)
        mainViewModel.getCartData()
    }

    override fun onStop() {
        binding.categoryTabLayout.clearOnTabSelectedListeners()
        super.onStop()
    }

    //    создание BottomSheetDialogFragment дял фильтрации
    private fun createBottomSheetDialogFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToFilterBottomSheetDialog()
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