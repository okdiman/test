package com.skillbox.skillbox.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.skillbox.skillbox.location.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.add_new_location.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import kotlin.random.Random

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var locationsList = arrayListOf<PointOfLocation>()
    private var locationsAdapter: LocationsListAdapter? = null

    private var rationaleDialog: AlertDialog? = null

    private var newLocation: PointOfLocation? = null

    private var selectedLocationInstant: Instant? = null


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
        rationaleDialog?.dismiss()
        rationaleDialog = null
        selectedLocationInstant = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!access()) {
            deniedAccessScreen()
        } else {
            initLocationsList()
            locationsAdapter?.items = locationsList
        }
        binding.addLocationButton.setOnClickListener {
            getLastLocation()
        }
        binding.agreementToGiveAccessButton.setOnClickListener {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
            if (!access()) {
                showRationaleDialog()
            } else {
                binding.notAccessTextView.isVisible = false
                binding.agreementToGiveAccessButton.isVisible = false
                binding.addLocationButton.isVisible = true
                initLocationsList()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(KEY_FOR_LIST, locationsList)
    }


    private fun initLocationsList() {
        binding.emptyListTextView.isVisible = true
        binding.notAccessTextView.isVisible = false
        binding.agreementToGiveAccessButton.isVisible = false
        binding.addLocationButton.isVisible = true

        locationsAdapter = LocationsListAdapter { position -> changeDateAndTime(position) }
        with(binding.locationsRecyclerView) {
            adapter = locationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }


    private fun showRationaleDialog() {
        rationaleDialog = AlertDialog.Builder(requireContext())
            .setMessage("We need to get access to your geolocation to make a location point. Please, click 'ok', if you agree to give it")
            .setPositiveButton("Ok") { _, _ -> checkPermission() }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun checkPermission() {
        if (!access()) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
            if (!access()) {
                deniedAccessScreen()
            } else {
                initLocationsList()
            }
        } else {
            initLocationsList()
        }
    }

    private fun deniedAccessScreen() {
        binding.notAccessTextView.isVisible = true
        binding.agreementToGiveAccessButton.isVisible = true
        binding.addLocationButton.isVisible = false
        Toast.makeText(
            requireContext(),
            "it is impossible to get a location without permission",
            Toast.LENGTH_SHORT
        ).show()

        binding.agreementToGiveAccessButton.setOnClickListener {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
            if (!access()) {
                showRationaleDialog()
            } else {
                binding.notAccessTextView.isVisible = false
                binding.agreementToGiveAccessButton.isVisible = false
                binding.addLocationButton.isVisible = true
                initLocationsList()
            }
        }
    }

    private fun access(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLastLocation() {
        val view = (view as ViewGroup).inflate(R.layout.add_new_location)
        AlertDialog.Builder(requireContext())
            .setTitle("Please, enter a link to photo for the location")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                if (view.addPhotoToNewLocation.text.isNotEmpty()) {
                    LocationServices.getFusedLocationProviderClient(requireContext())
                        .lastLocation
                        .addOnSuccessListener {
                            newLocation = PointOfLocation(
                                Random.nextLong(),
                                it.latitude,
                                it.longitude,
                                it.altitude,
                                it.speed,
                                it.accuracy,
                                selectedLocationInstant ?: Instant.now(),
                                view.addPhotoToNewLocation.text.toString()
                            )
                            locationsList.add(0, newLocation!!)
                            locationsAdapter?.items = locationsList
                            binding.locationsRecyclerView.scrollToPosition(0)
                            if (locationsList.isNotEmpty()) {
                                binding.emptyListTextView.isVisible = false
                            }
                            selectedLocationInstant = null
                        }
                        .addOnCanceledListener {
                            Toast.makeText(
                                requireContext(),
                                "permission denied",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "getting of location is failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "The link is empty, try again, please",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
    }

    private fun changeDateAndTime(position: Int) {
        val enterDataTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                            .atZone(ZoneId.systemDefault())

                        Toast.makeText(requireContext(), "Выбрано время: $selectedDateTime", Toast.LENGTH_SHORT).show()
                        selectedLocationInstant = selectedDateTime.toInstant()
                        locationsList[position].pointOfTime = selectedLocationInstant!!
                        locationsAdapter?.items = locationsList
                        selectedLocationInstant = null
                    },
                    enterDataTime.hour,
                    enterDataTime.minute,
                    true
                )
                    .show()
            },
            enterDataTime.year,
            enterDataTime.month.value - 1,
            enterDataTime.dayOfMonth
        )
            .show()
    }

    companion object {
        const val KEY_FOR_LIST = "key for list"
    }
}