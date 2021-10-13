package com.skillbox.skillbox.testonlineshop.fragments

import android.os.Bundle
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
import com.skillbox.skillbox.testonlineshop.databinding.PhonesFragmentBinding
import com.skillbox.skillbox.testonlineshop.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.utils.toast
import kotlinx.coroutines.flow.collect

class PhonesFragment : Fragment(R.layout.phones_fragment) {
    private val binding: PhonesFragmentBinding by viewBinding(PhonesFragmentBinding::bind)
    private val mainViewModel: MainFragmentViewModel by viewModels()
    private var hotSalesAdapter: HotSalesAdapter by autoCleared()
    private var bestSellersAdapter: BestSellersAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getMainScreenData()
        initStartScreen()
        bindViewModel()
    }

    //    инициализация стартового экрана
    private fun initStartScreen() {
//    создаем адаптеры для recycler view и настраеваем recyclerView
        hotSalesAdapter = HotSalesAdapter()
        with(binding.hotSalesRecyclerView) {
            adapter = hotSalesAdapter
            layoutManager =
                LinearLayoutManager(requireContext()).apply {
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
    }

//    подписываемся на обновления вьюмодели
    private fun bindViewModel() {
        lifecycleScope.launchWhenResumed {
            mainViewModel.productsStateFlow.collect { result ->
//                передаем адаптерам полученные списки айтемов
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