package com.skillbox.multithreading.threading

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.multithreading.databinding.FragmentThreadingBinding

class ThreadingFragment : Fragment() {
    private var _binding: FragmentThreadingBinding? = null
    private val binding get() = _binding!!

    private lateinit var handler: Handler
    private val mainHandler = Handler()

    private var moviesAdapter: AdapterThreading? = null

    private val moviesViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backgroundThread = HandlerThread("second").apply {
            start()
        }
        handler = Handler(backgroundThread.looper)
        initList()
        binding.initMoviesButton.setOnClickListener {
            handler.post {
                addMovies()
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            handler.post {
                addMovies()
            }
        }
        observerLiveData()
    }

    private fun initList() {
        moviesAdapter = AdapterThreading {}
        with(binding.moviesRecyclerView) {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun addMovies() {
        moviesViewModel.requestMovies()
    }

    private fun observerLiveData() {
        moviesViewModel.moviesLive.observe(viewLifecycleOwner) { newMovies ->
            mainHandler.post {
                moviesAdapter?.items = newMovies
                binding.swipeRefresh.isRefreshing = false
            }
        }
        moviesViewModel.showToast
            .observe(viewLifecycleOwner) {
                if (moviesViewModel.isNewMoviesEmpty) {
                    Toast.makeText(
                        requireContext(),
                        "Movies list is empty, please, check your Internet connection and try again",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    handler.postDelayed({
                        Toast.makeText(
                            requireContext(),
                            "Movies was added to list",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }, 1000)
                }
            }
    }
}