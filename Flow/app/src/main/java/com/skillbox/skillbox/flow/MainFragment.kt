package com.skillbox.skillbox.flow

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.flow.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
}