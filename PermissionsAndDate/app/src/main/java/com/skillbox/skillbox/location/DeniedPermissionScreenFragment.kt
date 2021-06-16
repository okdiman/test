package com.skillbox.skillbox.location

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.skillbox.skillbox.location.databinding.DeniedPermissionScreenFragmentBinding

class DeniedPermissionScreenFragment : Fragment() {
    private var _binding: DeniedPermissionScreenFragmentBinding? = null
    private val binding get() = _binding!!

    private val changeFragmentToMain: StartMainFragmentFromDenied
        get() = activity.let { it as StartMainFragmentFromDenied }

    private var rationaleDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeniedPermissionScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        rationaleDialog = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agreementToGiveAccessButton.setOnClickListener {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MainActivity.PERMISSION_REQUEST_CODE ->
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    initMainFragment()
                } else {
                    showRationaleDialog()
                }
        }
    }

    private fun showRationaleDialog() {
        rationaleDialog = AlertDialog.Builder(requireContext())
            .setMessage("We need to get access to your geolocation to make a location point. Please, click 'ok', if you agree to give it")
            .setPositiveButton("Ok") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MainActivity.PERMISSION_REQUEST_CODE
                )
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    requireContext(),
                    "it is impossible to get a location without permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun initMainFragment(){
        changeFragmentToMain.startMainFragmentFromDeniedFragment()
    }
}