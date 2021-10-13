package com.skillbox.skillbox.testonlineshop.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.adapters.BestSellersAdapter
import com.skillbox.skillbox.testonlineshop.adapters.HotSalesAdapter
import com.skillbox.skillbox.testonlineshop.databinding.MainFragmentBinding
import com.skillbox.skillbox.testonlineshop.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.utils.toast
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val mainViewModel: MainFragmentViewModel by viewModels()
    private var hotSalesAdapter: HotSalesAdapter by autoCleared()
    private var bestSellersAdapter: BestSellersAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartScreen()
        bindStateFlowAndLiveData()
    }

    private fun initStartScreen() {
        hotSalesAdapter = HotSalesAdapter()
        with(binding.hotSalesRecyclerView) {
            adapter = hotSalesAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }
        bestSellersAdapter = BestSellersAdapter()
        with(binding.bestSellersRecyclerView) {
            adapter = bestSellersAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
        mainViewModel.getAllProducts()
    }

    private fun bindStateFlowAndLiveData() {
        lifecycleScope.launchWhenResumed {
            mainViewModel.productsStateFlow.collect { result ->
                hotSalesAdapter.items = result?.homeStore
                bestSellersAdapter.items = result?.bestSellers
            }
        }
        lifecycleScope.launchWhenResumed {
            mainViewModel.isLoadingStateFlow.collect { binding.progressBar.isVisible = it }
        }
        mainViewModel.isErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
    }
}