package com.skillbox.multithreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skillbox.multithreading.databinding.RaceConditionFragmentBinding

class RaceConditionFragment : Fragment() {
    private var _binding: RaceConditionFragmentBinding? = null
    private val binding get() = _binding!!

    private val value = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RaceConditionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.incrementButton.setOnClickListener {
            increment()
        }
        binding.incrementWithSynchronyzeButton.setOnClickListener {
            syncIncrement()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun increment(){
        val threadCount = binding.countOfThreadsEditText.text.toString()
        val numbersForIncrement = binding.numberForIncrementEditText.text.toString()



    }

    private fun syncIncrement(){

    }
}