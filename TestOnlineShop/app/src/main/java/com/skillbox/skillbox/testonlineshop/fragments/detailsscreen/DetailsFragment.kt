package com.skillbox.skillbox.testonlineshop.fragments.detailsscreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.adapters.DetailsFragmentViewPagerAdapter
import com.skillbox.skillbox.testonlineshop.adapters.DetailsInfoAdapter
import com.skillbox.skillbox.testonlineshop.classes.Product
import com.skillbox.skillbox.testonlineshop.databinding.DetailsFragmentBinding
import com.skillbox.skillbox.testonlineshop.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.utils.toast
import kotlinx.coroutines.flow.collect
import recycler.coverflow.CoverFlowLayoutManger

class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val binding: DetailsFragmentBinding by viewBinding(DetailsFragmentBinding::bind)
    private val detailsViewModel: DetailsFragmentViewModel by viewModels()
    private var detailsInfoAdapter: DetailsInfoAdapter by autoCleared()

    //    создаем искусственно лист ссылок на изображения телефона, чтобы лучше показать карусель,
//    ибо в запросе приходит только 1 изображение
    private val imagesList = mutableListOf(
        "https://bornarayaneh.ir/wp-content/uploads/note-20-ultra-black.jpg",
        "https://milanooshop.com/wp-content/uploads/2021/04/SQ_vpavic_200807_4133_0107.jpeg"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bindViewModel()
    }

    //    инициализация стартового экрана
    private fun init() {
//        делаем запрос на получение инфы по продукту
        detailsViewModel.getDetailsProductInfo()
//        создаем и настраиваем адаптер для карусели
        detailsInfoAdapter = DetailsInfoAdapter()
        with(binding.detailsInfoRecyclerView) {
            adapter = detailsInfoAdapter
            layoutManager = CoverFlowLayoutManger(
                false, false, true, 0.8f
            )
            setHasFixedSize(true)
        }
        binding.backDetailsFragmentButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    //    инициализируем tabLayout и viewPager
    private fun initTabLayout(product: Product) {
        val list = listOf("Shop", "Details", "Features")
        binding.detailInfoViewPager.adapter = DetailsFragmentViewPagerAdapter(
            list, product, this
        )
        TabLayoutMediator(binding.detailsTabLayout, binding.detailInfoViewPager) { tab, position ->
            tab.text = list[position]
        }.attach()
    }


    //    подписываемся на обновления вьюмодели
    private fun bindViewModel() {
        lifecycleScope.launchWhenResumed {
            detailsViewModel.detailsInfoStateFlow.collect { product ->
                binding.run {
//                    если результат не null, то устанавливаем необходимые значения во все вьюшки
                    if (product != null) {
                        initTabLayout(product)
                        titleOfModelTextView.text = product.title
                        ratingBar.rating = product.rating!!
                        if (product.is_favorites) {
                            favoritesDetailsFragmentActionButton
                                .setImageResource(R.drawable.ic_favorite_full)
                        }
                        detailsInfoAdapter.items = product.images?.plus(imagesList)
//                        лисенер на кнопку favorites здесь потому что мы не получаем объект
//                        продукта во фрагмент из вне, поэтому не вижу смысла создавать
//                        объект продукта отдельно только чтобы создать лисенер при
//                        инициализации фрагмента, все равно обращение к серверу п
//                        роисходит в момент инициализации
                        favoritesDetailsFragmentActionButton.setOnClickListener {
                            if (product.is_favorites) {
                                product.is_favorites = false
                                favoritesDetailsFragmentActionButton
                                    .setImageResource(R.drawable.ic_favorite)
                            } else {
                                product.is_favorites = true
                                favoritesDetailsFragmentActionButton
                                    .setImageResource(R.drawable.ic_favorite_full)
                            }
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            detailsViewModel.isLoadingStateFlow.collect { binding.progressBar.isVisible = it }
        }
        detailsViewModel.isErrorLiveData.observe(viewLifecycleOwner) { toast(it) }
    }
}