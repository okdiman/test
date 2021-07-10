package com.skillbox.skillbox.networking.activityandfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        initMenu()
        initList()
        observeViewModel()
    }

    private fun initMenu() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_menu_layout,
            resources.getStringArray(R.array.movies_types_string_array)
        )
        AutoCompleteTextView.setAdapter(adapter)
    }

    private fun initList() {
        adapterMovie = AdapterMovies {}
        with(binding.movieRecyclerView) {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        movieViewModel.movie.observe(viewLifecycleOwner) { newMovies ->
            adapterMovie?.items = newMovies
        }
    }
}