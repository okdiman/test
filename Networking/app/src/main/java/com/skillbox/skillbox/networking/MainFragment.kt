package com.skillbox.skillbox.networking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.skillbox.skillbox.networking.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.item_menu_layout.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
    }

    private fun initMenu() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.movies_types_string_array)
        )
        AutoCompleteTextView.setAdapter(adapter)
    }
}