package com.skillbox.skillbox.location.activityandfragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.skillbox.location.databinding.OilCalculateFlowRateFragmentBinding

class OilCalculateFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultOilButton.setOnClickListener {
            if (allFieldsIsNotEmpty()) {
                calculateOilSpeed()
            } else {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateOilSpeed() {
        val flow = binding.oilFlowRateEditText.text.toString().toInt()
        val diameter = binding.oilInternalDiameterEditText.text.toString().toFloat()

        val speed = (flow * 1000) / (3.14 * 3.6 * (diameter / 2) * (diameter / 2))
        val speedByKmPerSec = speed * 3.6

        binding.oilResultTextView.text = "calculated speed: $speed m/s"
        binding.oilResultTextViewKm.text = "calculated speed: $speedByKmPerSec km/h"
    }

    private fun allFieldsIsNotEmpty(): Boolean {
        return (binding.oilFlowRateEditText.text.isNotEmpty() && binding.oilInternalDiameterEditText.text.isNotEmpty())
    }
}