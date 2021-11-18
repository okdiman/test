package com.skillbox.skillbox.testonlineshop.features.cart.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.core.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.databinding.CartFragmentBinding
import com.skillbox.skillbox.testonlineshop.features.cart.data.models.CartState
import com.skillbox.skillbox.testonlineshop.features.cart.presentation.adapters.CartInfoAdapter
import com.skillbox.skillbox.testonlineshop.features.cart.presentation.screens.viewmodel.CartFragmentViewModel
import com.skillbox.skillbox.testonlineshop.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.cart_fragment) {
    private val binding: CartFragmentBinding by viewBinding(CartFragmentBinding::bind)
    private val cartViewModel by viewModels<CartFragmentViewModel>()
    private var cartDetailsAdapter: CartInfoAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            backCartFragmentButton.setOnClickListener {
                findNavController().popBackStack()
            }
//            лисенер для обновления свайпом вверх
            cartSwipeRefreshLayout.setOnRefreshListener {
                cartViewModel.getCartInfo()
                binding.cartSwipeRefreshLayout.isRefreshing = false
            }
        }
        bindViewModel()
        init()
    }

    //    инициализация стартового экрана
    private fun init() {
        cartDetailsAdapter = CartInfoAdapter()
        with(binding.cartDetailsRV) {
            adapter = cartDetailsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        cartViewModel.getCartInfo()
        binding.backgroundImageView.run {
            initBackGroundImageView(this)
        }
    }

    private fun initBackGroundImageView(imageView: ImageView) {
//                создаем объект провайдера контура вью и указываем только верхние радиусы для закругления
        imageView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                val curveRadius = 60F
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
        imageView.clipToOutline = true
    }


    //    подписываемся на обновления вьюмодели
    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        cartViewModel.cartLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CartState.Loading -> binding.progressBar.isVisible = true
                is CartState.Error -> {
                    binding.progressBar.isVisible = false
                    toastLong(R.string.server_error)
                }
                is CartState.Success -> {
                    binding.run {
                        progressBar.isVisible = false
                        totalPriceTextView.text =
                            "$${DecimalFormat("#0.00").format(state.result.total)}"
                        totalDeliveryPriceTextView.text = state.result.delivery
                    }
                    cartDetailsAdapter.items = state.result.basket
                }
            }
        }
    }
}