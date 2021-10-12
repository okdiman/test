package com.skillbox.skillbox.materialdesign.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.materialdesign.R
import com.skillbox.skillbox.materialdesign.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindBottomNavigationBar()
    }

    private fun bindBottomNavigationBar() {

    }
}