package com.skillbox.skillbox.flow.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.flow.R
import com.skillbox.skillbox.flow.adapter.MovieAdapter
import com.skillbox.skillbox.flow.databinding.DatabaseFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DatabaseFragment : Fragment(R.layout.database_fragment) {
    private val binding: DatabaseFragmentBinding by viewBinding(DatabaseFragmentBinding::bind)
    private var movieDatabaseAdapter: MovieAdapter? = null
    private val viewModelDatabase: DatabaseFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindingViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieDatabaseAdapter = null
    }

    //    инициализация списка фильмов
    private fun initList() {
        movieDatabaseAdapter = MovieAdapter()
        with(binding.databaseRecyclerView) {
            adapter = movieDatabaseAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindingViewModel() {
        lifecycleScope.launch {
            viewModelDatabase.moviesListStateFlow.collect { moviesList ->
                movieDatabaseAdapter?.items = moviesList
            }
        }
    }
}