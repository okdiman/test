package com.skillbox.skillbox.flow.fragments

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.flow.R
import com.skillbox.skillbox.flow.adapter.MovieAdapter
import com.skillbox.skillbox.flow.broadcastreceiver.InternetConnectionBroadcastReceiver
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
    private var moviesAdapter: MovieAdapter? = null
    private val internetReceiver = InternetConnectionBroadcastReceiver()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowSearching()
        initList()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            internetReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(internetReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviesAdapter = null
    }

    private fun initList() {
        moviesAdapter = MovieAdapter()
        with(binding.listOfMoviesRecyclerView) {
            adapter = moviesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
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

    private fun bindViewModel() {
        viewModel.searching.observe(viewLifecycleOwner) { movies ->
            moviesAdapter?.items = movies
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            isLoading(loading)
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.run {
                progressBar.isVisible = true
                filmTitleEditText.isEnabled = false
                typeOfFilmRadioGroup.isEnabled = false
            }
        } else {
            binding.run {
                progressBar.isVisible = false
                filmTitleEditText.isEnabled = true
                typeOfFilmRadioGroup.isEnabled = true
            }
        }
    }
}