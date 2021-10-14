package com.skillbox.skillbox.testonlineshop.fragments.detailsscreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.adapters.DetailsInfoAdapter
import com.skillbox.skillbox.testonlineshop.databinding.DetailsFragmentBinding
import com.skillbox.skillbox.testonlineshop.utils.autoCleared
import com.skillbox.skillbox.testonlineshop.utils.toast
import kotlinx.coroutines.flow.collect
import recycler.coverflow.CoverFlowLayoutManger

class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val binding: DetailsFragmentBinding by viewBinding(DetailsFragmentBinding::bind)
    private val detailsViewModel: DetailsFragmentViewModel by viewModels()
    private var detailsInfoAdapter: DetailsInfoAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bindViewModel()
    }


    private fun init() {
        detailsViewModel.getDetailsProductInfo()
        detailsInfoAdapter = DetailsInfoAdapter()
        with(binding.detailsInfoRecyclerView) {
            adapter = detailsInfoAdapter
            layoutManager = CoverFlowLayoutManger(false, false, true, 0.8f)
            setHasFixedSize(true)
        }
        binding.run {
            colorOneActionButton.setOnClickListener {
                colorOneActionButton.setImageResource(R.drawable.ic_check)
                colorTwoActionButton.setImageState(IntArray(2), true)
            }
            colorTwoActionButton.setOnClickListener {
                colorTwoActionButton.setImageResource(R.drawable.ic_check)
                colorTwoActionButton.setImageState(IntArray(2), true)
            }
            capacityFirstButton.setOnClickListener {
                capacityFirstButton.isActivated
            }
        }
        binding.backDetailsFragmentButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    //    подписываемся на обновления вьюмодели
    private fun bindViewModel() {
        lifecycleScope.launchWhenResumed {
            detailsViewModel.detailsInfoStateFlow.collect { product ->
                binding.run {
                    if (product != null) {
                        titleOfModelTextView.text = product.title
                        ratingBar.rating = product.rating!!
                        processorTextView.text = product.cpu
                        cameraTextView.text = product.camera
                        memoryTextView.text = product.ssd
                        capacityTextView.text = product.sd
                        if (product.is_favorites) {
                            favoritesDetailsFragmentActionButton.setImageResource(R.drawable.ic_favorite_full)
                        }
                        val imagesList = mutableListOf(
                            "https://bornarayaneh.ir/wp-content/uploads/note-20-ultra-black.jpg",
                            "https://milanooshop.com/wp-content/uploads/2021/04/SQ_vpavic_200807_4133_0107.jpeg"
                        )
                        detailsInfoAdapter.items = product.images?.plus(imagesList)
                        favoritesDetailsFragmentActionButton.setOnClickListener {
                            if (product.is_favorites){
                                product.is_favorites = false
                                favoritesDetailsFragmentActionButton.setImageResource(R.drawable.ic_favorite)
                            } else{
                                product.is_favorites = true
                                favoritesDetailsFragmentActionButton.setImageResource(R.drawable.ic_favorite_full)
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