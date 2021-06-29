package com.skillbox.skillbox.location.activityandfragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.skillbox.skillbox.location.R
import com.skillbox.skillbox.location.adapter.LocationsListAdapter
import com.skillbox.skillbox.location.classes.PointOfLocation
import com.skillbox.skillbox.location.databinding.MainFragmentBinding
import com.skillbox.skillbox.location.inflate
import kotlinx.android.synthetic.main.add_new_location.view.*
import kotlinx.android.synthetic.main.add_photo_to_location.view.*
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

    private var selectedLocationInstant: Instant? = null

    private var locCall: LocationCallback? = null
    private var selectedItem: String? = null

    private var autoModeCheck: Boolean = false

    private val singleChoice = arrayListOf("Image", "Date and time")
    private val typeOfProduct = arrayListOf("Нефть", "Газ")

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
        selectedLocationInstant = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            locationsList =
                savedInstanceState.getParcelableArrayList<PointOfLocation>(KEY_FOR_LIST)!!
        }
        initLocationsList()
        locationsAdapter?.items = locationsList
        binding.addLocationButton.setOnClickListener {
            getLastLocation()
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.calculateFlowButton -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Выберите тип продукта")
                        .setSingleChoiceItems(typeOfProduct.toTypedArray(), 1) { _, i ->
                            selectedItem = typeOfProduct[i]
                        }
                        .setPositiveButton("ok") { _, _ ->
                            when (selectedItem) {
                                "Газ" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_gasCalculateFragment)
                                }
                                "Нефть" -> {
                                    findNavController().navigate(R.id.action_mainFragment_to_oilCalculateFragment)
                                }
                                else -> Toast.makeText(
                                    requireContext(),
                                    "Вы не выбрали тип продукта",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        .setNegativeButton("cancel") { _, _ -> }
                        .show()
                    true
                }
                R.id.turnOnOffAutoModeButton -> {
                    if (!autoModeCheck) {
                        menuItem.title = "Выключить режим записи маршрута"
                        startLocationUpdates()
                        autoModeCheck = true
                        Toast.makeText(
                            requireContext(),
                            "Включена запись маршрута",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        menuItem.title = "Включить режим записи маршрута"
                        stopLocationUpdates()
                        autoModeCheck = false
                        Toast.makeText(
                            requireContext(),
                            "Запись маршрута выключена",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_FOR_LIST, locationsList)
    }

    private fun initLocationsList() {
        binding.emptyListTextView.isVisible = true
        locationsAdapter = LocationsListAdapter { position -> changeDateAndTime(position) }
        with(binding.locationsRecyclerView) {
            adapter = locationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
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
                            if (it != null) {
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
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Unfortunately, we couldn't get location. Check your geolocation mode and Internet connection and try again, or we didn't find last location, try to turn on/turn off auto mode and try to set current location again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
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
            .show()
    }

    private fun changeDateAndTime(position: Int) {

        AlertDialog.Builder(requireContext())
            .setTitle("What do u want change?")
            .setSingleChoiceItems(singleChoice.toTypedArray(), 1) { _, i ->
                selectedItem = singleChoice[i]
            }
            .setPositiveButton("ok") { _, _ ->
                when (selectedItem) {
                    "Image" -> {
                        val view = (view as ViewGroup).inflate(R.layout.add_photo_to_location)
                        AlertDialog.Builder(requireContext())
                            .setTitle("Enter a link for the image")
                            .setView(view)
                            .setPositiveButton("Add") { _, _ ->
                                if (view.addPhotoToOldLocation.text.isNotEmpty()) {
                                    locationsList =
                                        locationsList.clone() as ArrayList<PointOfLocation>
                                    locationsList[position] =
                                        locationsList[position].copy(picture = view.addPhotoToOldLocation.text.toString())
                                    locationsAdapter?.items = locationsList
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "u didn't enter the link",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                            .setNegativeButton("cancel") { _, _ -> }
                            .show()
                    }
                    "Date and time" -> {
                        val enterDataTime = LocalDateTime.now()
                        DatePickerDialog(
                            requireContext(),
                            { _, year, month, dayOfMonth ->
                                TimePickerDialog(
                                    requireContext(),
                                    { _, hourOfDay, minute ->
                                        val selectedDateTime: ZonedDateTime =
                                            LocalDateTime.of(
                                                year,
                                                month + 1,
                                                dayOfMonth,
                                                hourOfDay,
                                                minute
                                            )
                                                .atZone(ZoneId.systemDefault())
                                        selectedLocationInstant = selectedDateTime.toInstant()
                                        locationsList =
                                            locationsList.clone() as ArrayList<PointOfLocation>
                                        locationsList[position] =
                                            locationsList[position].copy(pointOfTime = selectedLocationInstant!!)
                                        locationsAdapter?.items = locationsList
                                        Toast.makeText(
                                            requireContext(),
                                            "Выбрано время: $selectedDateTime",
                                            Toast.LENGTH_SHORT
                                        ).show()
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
                    else -> Toast.makeText(
                        requireContext(),
                        "you didn't choose action",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .setNegativeButton("cancel") { _, _ -> }
            .show()
        selectedItem = null

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
            locCall!!,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.removeLocationUpdates(locCall!!)
    }

    companion object {
        const val KEY_FOR_LIST = "key for list"
    }
}