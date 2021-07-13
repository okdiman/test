package com.skillbox.skillbox.networking.activityandfragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.networking.R
import com.skillbox.skillbox.networking.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: ViewModelMainFragment by viewModels()

    private var adapterMovie: AdapterMovies? = null


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
        initStartScreen()
        observeViewModel()
        binding.searchButton.setOnClickListener {
            if (binding.TitleMovieEditText.text.isNotEmpty()) {
                request()
            } else {
                Toast.makeText(requireContext(), "Enter title of film, please", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initStartScreen() {
        val adapterMenu = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.movies_types_string_array)
        )
        AutoCompleteTextView.setAdapter(adapterMenu)
        adapterMovie = AdapterMovies {}
        with(binding.movieRecyclerView) {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    private fun observeViewModel() {
        movieViewModel.movie.observe(viewLifecycleOwner) { newMovies ->
            if (newMovies.isEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setMessage("Sorry, an error has occurred, please, check your internet connection and try again")
                    .setNegativeButton("cancel") { _, _ -> }
                    .setPositiveButton("repeat") { _, _ -> request() }
                    .show()
            } else {
                adapterMovie?.items = newMovies
            }
        }
        movieViewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.movieRecyclerView.isVisible = isLoading.not()
        binding.progressBar.isVisible = isLoading
        binding.searchButton.isEnabled = isLoading.not()
        binding.TitleMovieEditText.isEnabled = isLoading.not()
        binding.YearMovieEditText.isEnabled = isLoading.not()
    }

    private fun request() {
        movieViewModel.requestMovies(
            binding.TitleMovieEditText.text.toString(),
            binding.YearMovieEditText.text.toString(),
            if (binding.AutoCompleteTextView.text.toString() == "Not chosen") {
                ""
            } else {
                binding.AutoCompleteTextView.text.toString()
            }
        )
    }
}