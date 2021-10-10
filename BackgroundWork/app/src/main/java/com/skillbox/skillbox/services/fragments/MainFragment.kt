package com.skillbox.skillbox.services.fragments

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.services.R
import com.skillbox.skillbox.services.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
}