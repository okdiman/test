package com.skillbox.skillbox.testonlineshop.presentation.cartfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.CartFragmentBinding

class CartFragment : Fragment(R.layout.cart_fragment) {
    private val binding: CartFragmentBinding by viewBinding(CartFragmentBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backCartFragmentButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}