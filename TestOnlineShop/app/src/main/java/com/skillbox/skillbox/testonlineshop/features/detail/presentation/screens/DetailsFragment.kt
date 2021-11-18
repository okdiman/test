package com.skillbox.skillbox.testonlineshop.features.detail.presentation.screens

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.core.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.databinding.DetailsFragmentBinding
import com.skillbox.skillbox.testonlineshop.features.detail.data.models.DetailsState
import com.skillbox.skillbox.testonlineshop.features.detail.domain.entities.Product
import com.skillbox.skillbox.testonlineshop.features.detail.presentation.adapters.detailsinfo.DetailsInfoAdapter
import com.skillbox.skillbox.testonlineshop.features.detail.presentation.adapters.viewpager.DetailsFragmentViewPagerAdapter
import com.skillbox.skillbox.testonlineshop.features.detail.presentation.screens.viewmodel.DetailsFragmentViewModel
import com.skillbox.skillbox.testonlineshop.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import recycler.coverflow.CoverFlowLayoutManger

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val binding: DetailsFragmentBinding by viewBinding(DetailsFragmentBinding::bind)
    private val detailsViewModel by viewModels<DetailsFragmentViewModel>()
    private var detailsInfoAdapter: DetailsInfoAdapter by autoCleared()

    //    создаем искусственно лист ссылок на изображения телефона, чтобы лучше показать карусель,
//    ибо в запросе приходит только 1 изображение
    private val imagesList = mutableListOf(
        "https://www.reliancedigital.in/medias/Samsung-Note20-Ultra-MobilePhones-491900824-i-5-1200Wx1200H?context=bWFzdGVyfGltYWdlc3wzNjY0NTB8aW1hZ2UvanBlZ3xpbWFnZXMvaGRkL2g5My85MzU0MDY2Mjk2ODYyLmpwZ3xiMzAzZGRiM2Q1ZDA3ZTI3YzE2ZGQ5NTAxNjc3YzZmY2EwYjAyNmVmNmM5MjFkMTc4ZTc3MTE0N2IxMTQ3MTgw",
        "https://www.giztop.com/media/catalog/product/cache/dc206057cdd42d7e34b9d36e347785ca/s/a/samsung_galaxy_note_20_ultra_case-_6.jpg"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bindViewModel()
    }

    //    инициализация экрана
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
        binding.run {
            backDetailsFragmentButton.setOnClickListener {
                findNavController().popBackStack()
            }
            cartDetailsFragmentButton.setOnClickListener {
                findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToCartFragment()
                )
            }
            detailsFragmentBackgroundImageView.run {
//                создаем объект провайдера контура вью
//                и указываем только верхние радиусы для закругления
                outlineProvider = object : ViewOutlineProvider() {
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
                clipToOutline = true
            }
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
        detailsViewModel.detailsInfoStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsState.Loading -> binding.progressBar.isVisible = true
                is DetailsState.Error -> {
                    binding.progressBar.isVisible = false
                    toastLong(R.string.server_error)
                }
                is DetailsState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.run {
                        initTabLayout(state.product)
                        titleOfModelTextView.text = state.product.title
                        ratingBar.rating = state.product.rating!!
                        if (state.product.is_favorites) {
                            favoritesDetailsFragmentActionButton
                                .setImageResource(R.drawable.ic_favorite_full)
                        }
                        detailsInfoAdapter.items = state.product.images?.plus(imagesList)
//                        лисенер на кнопку favorites здесь потому что мы не получаем объект
//                        продукта во фрагмент из вне, поэтому не вижу смысла создавать
//                        объект продукта отдельно только чтобы создать лисенер при
//                        инициализации фрагмента, все равно обращение к серверу п
//                        роисходит в момент инициализации
                        favoritesDetailsFragmentActionButton.setOnClickListener {
                            if (state.product.is_favorites) {
                                state.product.is_favorites = false
                                favoritesDetailsFragmentActionButton
                                    .setImageResource(R.drawable.ic_favorite)
                            } else {
                                state.product.is_favorites = true
                                favoritesDetailsFragmentActionButton
                                    .setImageResource(R.drawable.ic_favorite_full)
                            }
                        }
                    }
                }
            }
        }
    }
}