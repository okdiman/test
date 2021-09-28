package com.skillbox.skillbox.location.activityandfragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skillbox.skillbox.location.R
import kotlinx.android.synthetic.main.map_activity.*


class SecondActivity : FragmentActivity(R.layout.map_activity), OnMapReadyCallback {
    private val listOfTypesOfMap = arrayListOf<String>(
        "Normal",
        "Satellite",
        "Terrain",
        "Hybrid"
    )
    private var selectedItem: String? = null
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        typeOfMapButton.setOnClickListener {
            Log.i("map_click", "$map")
            changeMapType(map)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
        p0.mapType = GoogleMap.MAP_TYPE_SATELLITE
        map = p0
    }

    private fun changeMapType(map: GoogleMap) {
        AlertDialog.Builder(this)
            .setTitle("Choose type of map")
            .setSingleChoiceItems(listOfTypesOfMap.toTypedArray(), -1) { _, i ->
                selectedItem = listOfTypesOfMap[i]
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("ok") { _, _ ->
                when (selectedItem) {
                    "Normal" -> map.mapType = GoogleMap.MAP_TYPE_NORMAL
                    "Satellite" -> map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    "Terrain" -> map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    "Hybrid" -> map.mapType = GoogleMap.MAP_TYPE_HYBRID
                }
            }
            .show()
    }
}