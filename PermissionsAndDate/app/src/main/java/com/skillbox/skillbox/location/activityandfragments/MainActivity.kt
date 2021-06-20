package com.skillbox.skillbox.location.activityandfragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.skillbox.skillbox.location.R
import com.skillbox.skillbox.location.interfaces.StartMainFragmentFromDenied

class MainActivity : AppCompatActivity(), StartMainFragmentFromDenied {
    private var rationaleDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val googlePlayServiceAvailable =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        when {
            (googlePlayServiceAvailable == ConnectionResult.SUCCESS) -> {
                when {
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        startMainFragment()
                    }
                    shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                        showRationaleDialog()
                    }
                    else -> startDeniedPermissionFragment()
                }
            }
            else -> {
                if (GoogleApiAvailability.getInstance()
                        .isUserResolvableError(googlePlayServiceAvailable)
                ) {
                    GoogleApiAvailability.getInstance()
                        .getErrorDialog(this, googlePlayServiceAvailable, 9950)
                } else {
                    error("error, we couldn't check Google Play Services status, sorry")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    startMainFragment()
                } else {
                    startDeniedPermissionFragment()
                }
        }
    }


    private fun startMainFragment() {
        this.supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MapFragment())
                .commit()
//        rationaleDialog = null
//        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) != null
//        if (!alreadyHasFragment) {
//            this.supportFragmentManager.beginTransaction()
//                .replace(R.id.mainContainer, MainFragment())
//                .commit()
//        }
    }

    private fun startDeniedPermissionFragment() {
        rationaleDialog = null
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) != null
        if (!alreadyHasFragment) {
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, DeniedPermissionScreenFragment())
                .commit()
        }
    }

    private fun showRationaleDialog() {
        rationaleDialog = AlertDialog.Builder(this)
            .setMessage("We need to get access to your geolocation to make a location point. Please, click 'ok', if you agree to give it")
            .setPositiveButton("Ok") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_CODE
                )
            }
            .setNegativeButton("Cancel") { _, _ ->
                startDeniedPermissionFragment()
                Toast.makeText(
                    this,
                    "it is impossible to get a location without permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    override fun startMainFragmentFromDeniedFragment() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, MainFragment())
            .commit()
    }
}