package com.skillbox.skillbox.testonlineshop.features.main.presentation.screens

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
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding
import com.skillbox.skillbox.testonlineshop.features.main.data.models.MainScreenState
import com.skillbox.skillbox.testonlineshop.features.main.data.models.PhonesScreenState
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
//                ?????????????? ???????????? ???????????????????? ?????????????? ??????
//                ?? ?????????????????? ???????????? ?????????????? ?????????????? ?????? ??????????????????????
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
//                ?????????????????? ?????????????????? ??????????????
                clipToOutline = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        ?????????????????????????? ?????????????????? ???????????? ???????????????? ???????????? ?????? ?????????????????????? ???? ????????
        binding.bottomAppBar.selectedItemId = R.id.explorerItemBottomBar
    }

    //    ?????????????????????????? tabLayout ?? viewPager
    private fun init() {
//    ???????????? ???????????? ?????? tabLayout
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
//                ?????????????????????????? ?????????????? ????????, ???????? ???????????? ?? ???????? ???????????? ?????? ???????????? ??????????????
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
//        ?????????????? ???????????? ???????????????? ?????? tabLayout
//        ?????????????????????????? ?????????????? ????????, ???????? ???????????? ?? ???????? ???????????? ?? ?????????????????????? ???? ????????,
//        ???????????? ?????????????? ?????? ??????
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

    //    ???????????????? BottomSheetDialogFragment ?????? ????????????????????
    private fun createBottomSheetDialogFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToFilterBottomSheetDialog()
        )
    }

    //    ???????????????? ???? ?????????????????? ?????? ????????????
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