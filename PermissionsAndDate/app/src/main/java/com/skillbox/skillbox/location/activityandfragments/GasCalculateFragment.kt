package com.skillbox.skillbox.location.activityandfragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.skillbox.location.databinding.GasCalculateFlowRateFragmentBinding

class GasCalculateFragment : Fragment() {
    private var _binding: GasCalculateFlowRateFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GasCalculateFlowRateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calculateGasButton.setOnClickListener {
            if (allFieldsIsNotEmpty()) {
                calculateSpeed()
            } else {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateSpeed() {
        val flow = binding.flowRateEditText.text.toString().toInt()
        val internalDiameter = binding.internalDiametrEditText.text.toString().toFloat()
        val pressure = binding.pressureEditText.text.toString().toFloat()
        val temperature = binding.temperatureEditText.text.toString().toInt()

        val requestSpeed =
            (0.1273 * flow * 1 * temperature) / (internalDiameter * internalDiameter * pressure)

        val speedByKmPerSec = requestSpeed * 3.6

        binding.gasResultTextView.text = "calculated speed: $requestSpeed m/s"
        binding.gasResultTextViewKm.text = "calculated speed: $speedByKmPerSec km/h"
    }

    private fun allFieldsIsNotEmpty(): Boolean {
        return (binding.flowRateEditText.text.isNotEmpty() && binding.internalDiametrEditText.text.isNotEmpty() &&
                binding.pressureEditText.text.isNotEmpty() && binding.temperatureEditText.text.isNotEmpty())
    }
}