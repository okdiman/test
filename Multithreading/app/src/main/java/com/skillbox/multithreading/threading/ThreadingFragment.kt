package com.skillbox.multithreading.threading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.skillbox.multithreading.databinding.FragmentThreadingBinding
import com.skillbox.multithreading.networking.Network

class ThreadingFragment : Fragment() {
    private var _binding: FragmentThreadingBinding? = null
    private val binding get() = _binding!!

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
        initList()
        binding.initMoviesButton.setOnClickListener {
            Thread{
                val newMovie = Network.getMovieById("tt10541088")
                if (newMovie != null) {
                    moviesViewModel.addMovie(newMovie.title, newMovie.year)
                }
//                Network.getMovieById("tt1190634")
//                Network.getMovieById("tt0068646")
//                Network.getMovieById("tt0071562")
//                Network.getMovieById("tt0110912")
//                Network.getMovieById("tt0120737")
//                Network.getMovieById("tt1375666")
//                Network.getMovieById("tt0080684")
            }.start()
            initList()
        }
    }

    private fun initList() {
        moviesViewModel.movies.observe(viewLifecycleOwner) { newMovies ->
            moviesAdapter?.items = newMovies
        }
    }

}