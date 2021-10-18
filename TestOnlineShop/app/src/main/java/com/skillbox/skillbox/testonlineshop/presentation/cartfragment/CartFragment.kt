package com.skillbox.skillbox.testonlineshop.presentation.cartfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.CartFragmentBinding
import com.skillbox.skillbox.testonlineshop.presentation.adapters.cartfragment.CartInfoAdapter
import com.skillbox.skillbox.testonlineshop.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
        binding.backgroundImageView.clipToOutline = true
    }


    //    подписываемся на обновления вьюмодели
    @SuppressLint("SetTextI18n")
    private fun bindViewModel() {
        lifecycleScope.launchWhenResumed {
            cartViewModel.cartStateFlow.collect { result ->
                cartDetailsAdapter.items = result?.basket
                binding.run {
                    totalPriceTextView.text = "$${result?.total} us"
                    totalDeliveryPriceTextView.text = result?.delivery
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            cartViewModel.isLoadingStateFlow.collect {
                binding.progressBar.isVisible = it
            }
        }
        lifecycleScope.launchWhenResumed {
            cartViewModel.isErrorLiveData.collect { error ->
                if (error) {
                    toastLong(R.string.server_error)
                }
            }
        }
    }
}