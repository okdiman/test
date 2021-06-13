package com.skillbox.skillbox.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.skillbox.location.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var locationsList = arrayListOf<PointOfLocation>()
    private var locationsAdapter: LocationsListAdapter? = null

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
        locationsAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocationsList()
        locationsAdapter?.items = locationsList
        binding.addLocationButton.setOnClickListener {
            getLastLocation()
        }
    }

    private fun initLocationsList() {
        locationsAdapter = LocationsListAdapter { }
        with(binding.locationsRecyclerView) {
            adapter = locationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun getLastLocation(){

    }


}