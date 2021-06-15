package com.skillbox.skillbox.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.skillbox.skillbox.location.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.add_new_location.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import kotlin.random.Random


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var locationsList = arrayListOf<PointOfLocation>()
    private var locationsAdapter: LocationsListAdapter? = null

    private var rationaleDialog: AlertDialog? = null
    private var selectedLocationInstant: Instant? = null

    private var locCall: LocationCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val currentLocation = locationResult.lastLocation
                val newLocation = PointOfLocation(
                    Random.nextLong(),
                    currentLocation.latitude,
                    currentLocation.longitude,
                    currentLocation.altitude,
                    currentLocation.speed,
                    currentLocation.accuracy,
                    selectedLocationInstant ?: Instant.now(),
                    "${R.drawable.autocheckpoint}"
                )
                locationsList = locationsList.clone() as ArrayList<PointOfLocation>
                locationsList.add(0, newLocation)
                locationsAdapter?.items = locationsList
                binding.locationsRecyclerView.scrollToPosition(0)
                if (locationsList.isNotEmpty()) {
                    binding.emptyListTextView.isVisible = false
                }
                selectedLocationInstant = null
            }

        }
        locCall = locationCallback
    }

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
        val googlePlayServiceAvailable =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())
        when {
            (googlePlayServiceAvailable == ConnectionResult.SUCCESS) -> {
                checkPermission()
                binding.addLocationButton.setOnClickListener {
                    getLastLocation()
                    locationsAdapter?.items = locationsList
                    selectedLocationInstant = null
                }
                binding.agreementToGiveAccessButton.setOnClickListener {
                    checkPermission()
                }
                startLocationUpdates()
            }
            (googlePlayServiceAvailable == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Service version update required")
                    .setPositiveButton("update now") { _, _ -> TODO("ссылка на обновление в play market") }
                    .setNegativeButton("later") { _, _ -> }
                    .show()
            }
            (googlePlayServiceAvailable == ConnectionResult.SERVICE_MISSING) -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Google Play services are missing! Your should install it to continue")
                    .setPositiveButton("Install now") { _, _ -> TODO("ссылка на установку из play market") }
                    .setNegativeButton("later") { _, _ -> }
            }
            else -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Something wrong, sorry. Google play services aren't working:(")
            }
        }

    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
////        outState.putParcelableArrayList(KEY_FOR_LIST, locationsList)
//    }

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
            .setPositiveButton("Ok") { _, _ -> permitRequest() }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    requireContext(),
                    "it is impossible to get a location without permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }


    private fun checkPermission() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (isLocationPermissionGranted) {
            grantedAccessScreen()
            initLocationsList()
        } else {
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (needRationale) {
                showRationaleDialog()
            } else {
                deniedAccessScreen()
                permitRequest()
            }
        }
    }

    private fun grantedAccessScreen() {
        binding.notAccessTextView.isVisible = false
        binding.agreementToGiveAccessButton.isVisible = false
        binding.addLocationButton.isVisible = true
    }

    private fun deniedAccessScreen() {
        binding.notAccessTextView.isVisible = true
        binding.agreementToGiveAccessButton.isVisible = true
        binding.addLocationButton.isVisible = false
    }

    private fun permitRequest() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLastLocation() {
        lateinit var newLocation: PointOfLocation
        val view = (view as ViewGroup).inflate(R.layout.add_new_location)
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        AlertDialog.Builder(requireContext())
            .setTitle("Please, enter a link to photo for the location")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                if (view.addPhotoToNewLocation.text.isNotEmpty()) {
                    fusedLocationClient.lastLocation
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
                            locationsList = locationsList.clone() as ArrayList<PointOfLocation>
                            locationsList.add(0, newLocation)
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
                        val selectedDateTime: ZonedDateTime =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                .atZone(ZoneId.systemDefault())
                        selectedLocationInstant = selectedDateTime.toInstant()
                        locationsList = locationsList.clone() as ArrayList<PointOfLocation>
                        locationsList[position].pointOfTime = selectedLocationInstant!!
                        locationsAdapter?.items = locationsList
                        Toast.makeText(
                            requireContext(),
                            "Выбрано время: $selectedDateTime",
                            Toast.LENGTH_SHORT
                        ).show()

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

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationRequest = LocationRequest.create().apply {
            interval = java.util.concurrent.TimeUnit.SECONDS.toMillis(60)
            fastestInterval = java.util.concurrent.TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = java.util.concurrent.TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locCall,
            Looper.getMainLooper()
        )
    }
//    override fun onPause() {
//        super.onPause()
//        stopLocationUpdates()
//    }
//
//    private fun stopLocationUpdates() {
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }

    companion object {
        const val KEY_FOR_LIST = "key for list"
    }
}