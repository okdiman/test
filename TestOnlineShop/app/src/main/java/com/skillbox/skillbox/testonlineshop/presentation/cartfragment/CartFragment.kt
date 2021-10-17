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
import com.skillbox.skillbox.testonlineshop.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.cart_fragment) {
    private val binding: CartFragmentBinding by viewBinding(CartFragmentBinding::bind)
    private val cartViewModel: CartFragmentViewModel by viewModels()
    private var cartDetailsAdapter: CartInfoAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backCartFragmentButton.setOnClickListener {
            findNavController().popBackStack()
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
            cartViewModel.isLoadingStateFlow.collect { binding.progressBar.isVisible = it }
        }
        cartViewModel.isErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
    }
}