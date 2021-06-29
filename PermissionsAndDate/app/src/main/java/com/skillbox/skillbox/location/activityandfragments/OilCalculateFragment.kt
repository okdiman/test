package com.skillbox.skillbox.location.activityandfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skillbox.skillbox.location.databinding.OilCalculateFlowRateFragmentBinding

class OilCalculateFragment: Fragment() {
    private var _binding: OilCalculateFlowRateFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OilCalculateFlowRateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}