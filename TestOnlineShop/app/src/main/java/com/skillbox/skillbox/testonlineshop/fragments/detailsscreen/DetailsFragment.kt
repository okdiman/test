package com.skillbox.skillbox.testonlineshop.fragments.detailsscreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.DetailsFragmentBinding
import com.skillbox.skillbox.testonlineshop.utils.toast
import kotlinx.coroutines.flow.collect

class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val binding: DetailsFragmentBinding by viewBinding(DetailsFragmentBinding::bind)
    private val detailsViewModel: DetailsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        bindViewModel()
    }

    private fun init() {
        detailsViewModel.getDetailsProductInfo()
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