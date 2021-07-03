package com.skillbox.skillbox.location.activityandfragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            calculateOilSpeed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateOilSpeed() {
        val flow = binding.oilFlowRateEditText.text.toString().toInt()
        val diameter = binding.oilInternalDiameterEditText.text.toString().toFloat()

        val speed = (flow * 1000) / (3.14 * 3.6 * (diameter / 2) * (diameter / 2))

        binding.oilResultTextView.text = "расчетная скорость потока равна = $speed м/с"
    }
}