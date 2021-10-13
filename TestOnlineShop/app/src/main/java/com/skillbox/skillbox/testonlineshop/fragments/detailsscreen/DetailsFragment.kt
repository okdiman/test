package com.skillbox.skillbox.testonlineshop.fragments.detailsscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.testonlineshop.R
import com.skillbox.skillbox.testonlineshop.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val binding: DetailsFragmentBinding by viewBinding(DetailsFragmentBinding::bind)
    private val detailsViewModel: DetailsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel.getDetailsProductInfo()
    }
}