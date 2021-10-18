package com.skillbox.skillbox.testonlineshop.presentation.mainfragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.PhonesFragmentBinding
import com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment.BestSellersAdapter
import com.skillbox.skillbox.testonlineshop.presentation.adapters.mainfragment.HotSalesAdapter
import com.skillbox.skillbox.testonlineshop.presentation.mainfragment.viewmodel.MainScreenViewModel
import com.skillbox.skillbox.testonlineshop.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.utils.isConnected
import com.skillbox.skillbox.testonlineshop.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PhonesFragment : Fragment(R.layout.phones_fragment) {
    private val binding: PhonesFragmentBinding by viewBinding(PhonesFragmentBinding::bind)
    private val mainViewModel by viewModels<MainScreenViewModel>()
    private var hotSalesAdapter: HotSalesAdapter by autoCleared()
    private var bestSellersAdapter: BestSellersAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartScreen()
        bindViewModel()
//        лисенер на обновление экрана свайпом вверх
        binding.phonesSwipeRefreshLayout.setOnRefreshListener {
            mainViewModel.getMainScreenData()
            binding.phonesSwipeRefreshLayout.isRefreshing = false
        }
    }

    //    инициализация стартового экрана
    private fun initStartScreen() {
//    создаем адаптеры для recycler view и настраиваем его
        hotSalesAdapter = HotSalesAdapter {
//            могли бы передать тут во фрагмент корзины выбранный нами элемент списка,
//            но такой пункт в задании отсутсвует
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
        }
        with(binding.hotSalesRecyclerView) {
            adapter = hotSalesAdapter
            layoutManager =
                LinearLayoutManager(requireContext()).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            setHasFixedSize(true)
        }
        bestSellersAdapter = BestSellersAdapter {
//            здесь мы бы могли передать продукт или id продукта по клику, для дальнейшего запроса
//            в след экран, но в данном ТЗ это не имеет смысла,
//            так как мы все равно получаем 1 и тот же элемент
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
        }
        with(binding.bestSellersRecyclerView) {
            adapter = bestSellersAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
        startRequest()
    }

    //    стартовый запрос на получение инфы о продуктах
    private fun startRequest() {
//        проверяем наличие интернет соединения
        if (requireContext().isConnected) {
            mainViewModel.getMainScreenData()
        } else {
//            выбрасываем диалог в случае отсутсвия сети
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.internet_error)
                .setMessage(R.string.inet_is_not_available)
                .setPositiveButton("Try again") { _, _ -> startRequest() }
                .show()
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
            mainViewModel.isLoadingStateFlow.collect {
                binding.progressBar.isVisible = it
            }
        }
        lifecycleScope.launchWhenResumed {
            mainViewModel.isErrorLiveData.collect { error ->
                if (error) {
                    toastLong(R.string.server_error)
                }
            }
        }
    }
}