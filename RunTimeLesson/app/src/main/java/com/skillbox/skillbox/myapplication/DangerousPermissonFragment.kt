package com.skillbox.skillbox.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.skillbox.skillbox.myapplication.databinding.DangerousPermessionFragmentBinding


class DangerousPermissionFragment : Fragment() {
    private var _binding: DangerousPermessionFragmentBinding? = null
    private val binding get() = _binding!!

    private var rationaleDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DangerousPermessionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        rationaleDialog = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getCurrentLocationButton.setOnClickListener {
            showCurrentLocationPermissionCheck()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            showLocationInfo()
        } else {
            showLocationRationaleDialog()
        }
    }

    private fun showLocationInfo() {
        Toast.makeText(requireContext(), "showLocation", Toast.LENGTH_SHORT).show()
    }



    private fun showCurrentLocationPermissionCheck() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isLocationPermissionGranted) {
            showLocationInfo()
        } else {
            val needRationale  = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (needRationale){
                showLocationRationaleDialog()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS)
    }

    private fun showLocationRationaleDialog (){
        rationaleDialog = AlertDialog.Builder(requireContext())
            .setMessage("need for getting location")
            .setPositiveButton("OK") { _, _ -> requestLocationPermission() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    companion object {
        const val REQUEST_PERMISSIONS = 1
    }
}