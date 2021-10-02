package com.skillbox.skillbox.flow.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.flow.R
import com.skillbox.skillbox.flow.classes.MovieType
import com.skillbox.skillbox.flow.databinding.MainFragmentBinding
import com.skillbox.skillbox.flow.utils.elementChangeFlow
import com.skillbox.skillbox.flow.utils.textChangesFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private val viewModel: MainFragmentViewModel by viewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowSearching()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun flowSearching() {
        viewLifecycleOwner.lifecycleScope.launch {
            val movieType = binding.typeOfFilmRadioGroup.elementChangeFlow().onStart { emit(0) }
                .map {
                    MovieType.values()[it]
                }
            val movieTitle = binding.filmTitleEditText.textChangesFlow().onStart { emit("") }
            viewModel.bind(movieTitle, movieType)
        }
    }
}