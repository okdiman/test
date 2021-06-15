package com.skillbox.skillbox.location

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : AppCompatActivity() {
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
            (googlePlayServiceAvailable == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) -> {
                AlertDialog.Builder(this)
                    .setTitle("Service version update required")
                    .setPositiveButton("update now") { _, _ -> TODO("ссылка на обновление в play market") }
                    .setNegativeButton("later") { _, _ -> }
                    .show()
            }
            (googlePlayServiceAvailable == ConnectionResult.SERVICE_MISSING) -> {
                AlertDialog.Builder(this)
                    .setTitle("Google Play services are missing! Your should install it to continue")
                    .setPositiveButton("Install now") { _, _ -> TODO("ссылка на установку из play market") }
                    .setNegativeButton("later") { _, _ -> }
            }
            else -> {
                AlertDialog.Builder(this)
                    .setTitle("Something wrong, sorry. Google play services aren't working:(")
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
        rationaleDialog = null
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) != null
        if (!alreadyHasFragment) {
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment())
                .commit()
        }
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
}