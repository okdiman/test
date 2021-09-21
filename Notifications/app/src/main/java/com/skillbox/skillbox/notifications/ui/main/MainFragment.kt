package com.skillbox.skillbox.notifications.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.notifications.R
import com.skillbox.skillbox.notifications.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}