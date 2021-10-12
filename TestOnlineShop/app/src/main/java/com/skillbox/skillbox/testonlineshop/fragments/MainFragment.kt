package com.skillbox.skillbox.testonlineshop.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val mainViewModel: MainFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("listOfProducts", "start main fragment")
        mainViewModel.getAllProducts()
    }
}