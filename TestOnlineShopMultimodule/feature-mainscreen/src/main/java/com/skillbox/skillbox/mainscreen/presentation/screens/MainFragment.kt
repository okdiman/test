package com.skillbox.skillbox.mainscreen.presentation.screens

import android.graphics.Color
import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.mainscreen.R
import com.skillbox.skillbox.mainscreen.databinding.MainFragmentBinding
import com.skillbox.skillbox.mainscreen.data.models.MainScreenState
import com.skillbox.skillbox.mainscreen.domain.entities.TypesOfProducts
import com.skillbox.skillbox.mainscreen.presentation.adapters.viewpager.MainFragmentViewPagerAdapter
import com.skillbox.skillbox.mainscreen.presentation.screens.viewmodel.MainScreenViewModel
import com.skillbox.skillbox.testonlineshop.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val mainViewModel by viewModel<MainScreenViewModel>()

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
                        R.id.cartItemBottomBar -> navigate(R.id.action_phonesFragment_to_cartFragment)
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
//                устанавливаем фоновый цвет, цвет текста и цвет иконки для первой позиции
                if (position == 0) {
                    customView?.run {
                        findViewById<ImageView>(R.id.tabBacgroundView)
                            ?.setImageDrawable(ColorDrawable(Color.parseColor("#FF6E4E")))
                        findViewById<TextView>(R.id.tabTextView)
                            ?.setTextColor(Color.parseColor("#FF6E4E"))
                        findViewById<ImageView>(R.id.tabImageView)
                            ?.setColorFilter(
                                ContextCompat.getColor(requireContext(), R.color.white),
                                PorterDuff.Mode.MULTIPLY
                            )
                    }
                }
            }
        }.attach()
//        создаем объект лисенера для tabLayout
//        устанавливаем фоновый цвет, цвет текста и цвет иконки в зависимости от того,
//        выбран элемент или нет
        val listener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.run {
                    findViewById<ImageView>(R.id.tabBacgroundView)
                        ?.setImageDrawable(ColorDrawable(Color.parseColor("#FF6E4E")))
                    findViewById<TextView>(R.id.tabTextView)
                        ?.setTextColor(Color.parseColor("#FF6E4E"))
                    findViewById<ImageView>(R.id.tabImageView)
                        ?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.white)
                        )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.run {
                    findViewById<ImageView>(R.id.tabBacgroundView)
                        ?.setImageDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))
                    findViewById<TextView>(R.id.tabTextView)
                        ?.setTextColor(Color.parseColor("#B3B3C3"))
                    findViewById<ImageView>(R.id.tabImageView)
                        ?.setColorFilter(
                            ContextCompat.getColor(requireContext(), R.color.image_color),
                            PorterDuff.Mode.MULTIPLY
                        )
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        }
        binding.mainViewPager.isUserInputEnabled = false
        binding.categoryTabLayout.addOnTabSelectedListener(listener)
        mainViewModel.getCartData()
    }

    override fun onStop() {
        binding.categoryTabLayout.clearOnTabSelectedListeners()
        super.onStop()
    }

    //    создание BottomSheetDialogFragment дял фильтрации
    private fun createBottomSheetDialogFragment() {
        navigate(R.id.action_mainFragment_to_filterBottomSheetDialog)
    }

    //    подписка на изменения вью модели
    private fun bindingViewModel() {
        mainViewModel.cartLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainScreenState.Success -> {
                    binding.bottomAppBar.getOrCreateBadge(R.id.cartItemBottomBar).run {
                        isVisible = true
                        number = state.result.basket.size
                    }
                }
                else -> {}
            }
        }
    }
}