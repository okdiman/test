package com.skillbox.skillbox.mainscreen.presentation.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.core.utils.autoCleared
import com.skillbox.skillbox.mainscreen.R
import com.skillbox.skillbox.mainscreen.databinding.PhonesFragmentBinding
import com.skillbox.skillbox.mainscreen.data.models.PhonesScreenState
import com.skillbox.skillbox.mainscreen.presentation.adapters.bestsellers.BestSellersAdapter
import com.skillbox.skillbox.mainscreen.presentation.adapters.hotsales.HotSalesAdapter
import com.skillbox.skillbox.mainscreen.presentation.screens.viewmodel.MainScreenViewModel
import com.skillbox.skillbox.testonlineshop.utils.isConnected
import com.skillbox.skillbox.testonlineshop.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint

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
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
        }
        with(binding.hotSalesRecyclerView) {
            adapter = hotSalesAdapter
//            доводчик для карусели
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
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
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
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
                .setCancelable(false)
                .setTitle(R.string.internet_error)
                .setMessage(R.string.inet_is_not_available)
                .setPositiveButton("Try again") { _, _ -> startRequest() }
                .show()
        }
    }

    //    подписываемся на обновления вьюмодели
    private fun bindViewModel() {
        mainViewModel.productsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PhonesScreenState.Success -> {
                    binding.progressBar.isVisible = false
//                передаем адаптерам полученные списки айтемов
                    hotSalesAdapter.items = state.result.homeStore
                    bestSellersAdapter.items = state.result.bestSellers
                }
                is PhonesScreenState.Error -> {
                    binding.progressBar.isVisible = false
                    toastLong(R.string.server_error)
                }
                is PhonesScreenState.Loading -> binding.progressBar.isVisible = true
            }
        }
    }
}