package com.skillbox.multithreading

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.multithreading.databinding.RaceConditionFragmentBinding

class RaceConditionFragment : Fragment() {
    private var _binding: RaceConditionFragmentBinding? = null
    private val binding get() = _binding!!

    private var value: Long = 0

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
            if (fieldsIsNotEmpty()) {
                value = 0
                increment()
            } else {
                makeToast()
            }
        }
        binding.incrementWithSynchronyzeButton.setOnClickListener {
            if (fieldsIsNotEmpty()) {
                value = 0
                syncIncrement()
            } else {
                makeToast()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun increment() {

        val threadCount = binding.countOfThreadsEditText.text.toString().toLong()
        val incrementCount = binding.numberForIncrementEditText.text.toString().toLong()
        val expectedValue = value + threadCount * incrementCount
        val startTime = System.currentTimeMillis()

        (0 until threadCount).map {
            Thread {
                value += incrementCount
            }.apply {
                start()
            }
        }
            .map { it.join() }
        val requestTime = System.currentTimeMillis() - startTime
        binding.mainTextView.text =
            "Expected value = $expectedValue, resulting value = $value, request time = $requestTime"
    }

    @SuppressLint("SetTextI18n")
    private fun syncIncrement() {
        val threadCount = binding.countOfThreadsEditText.text.toString().toInt()
        val incrementCount = binding.numberForIncrementEditText.text.toString().toInt()
        val expectedValue = value + threadCount * incrementCount
        val startTime = System.currentTimeMillis()

        (0 until threadCount).map {
            Thread {
                synchronized(this) {
                    value += incrementCount
                }
            }.apply {
                start()
            }
        }
            .map { it.join() }
        val requestTime = System.currentTimeMillis() - startTime
        binding.mainTextView.text =
            "Expected value = $expectedValue, resulting value = $value, request time = $requestTime"
    }

    private fun fieldsIsNotEmpty(): Boolean {
        return (binding.countOfThreadsEditText.text.isNotEmpty() && binding.numberForIncrementEditText.text.isNotEmpty())
    }

    private fun makeToast() {
        Toast.makeText(requireContext(), "fill all fields, please, and try again", Toast.LENGTH_SHORT)
            .show()
    }
}