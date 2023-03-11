package com.example.weatherappusingopenmeteo.utils
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionCheck(private val activity: Activity) {


    val LOCATION_PERMISSION_REQUEST_CODE = 101
    fun requestLocationPermission(): Boolean {

        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationTurnOnDialog()
            return false
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
            return false
        }
        return true
    }


     fun showRationaleDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Location Permission")
            .setMessage("This app needs location permission to provide you with better service.")
            .setPositiveButton("Allow") { _, _ ->
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
            .setNegativeButton("Deny") { _, _ -> }
            .create()
            .show()
    }

    private fun showLocationTurnOnDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Location Turn On")
            .setMessage("Please turn on your device location to use this feature.")
            .setPositiveButton("Turn On") { _, _ ->
                activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
    }
}

