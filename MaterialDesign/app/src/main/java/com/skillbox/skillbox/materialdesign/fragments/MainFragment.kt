package com.skillbox.skillbox.materialdesign.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skillbox.skillbox.materialdesign.R
import com.skillbox.skillbox.materialdesign.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carFragment = CarFragment()
        val busFragment = BusFragment()
        val railwayFragment = RailwayFragment()
        val planeFragment = AirplaneFragment()
        bindBottomNavigationBar(carFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.cars_page -> bindBottomNavigationBar(carFragment)
                R.id.buses_page -> bindBottomNavigationBar(busFragment)
                R.id.planes_page -> bindBottomNavigationBar(planeFragment)
                R.id.railways_page -> bindBottomNavigationBar(railwayFragment)
            }
            true
        }
    }

    private fun bindBottomNavigationBar(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentWrapper, fragment)
            commit()
        }
    }
}