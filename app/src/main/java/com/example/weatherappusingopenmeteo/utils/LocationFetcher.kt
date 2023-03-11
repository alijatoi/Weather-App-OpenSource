package com.example.weatherappusingopenmeteo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.io.IOException
import java.util.*

class LocationFetcher(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onSuccess: (Location?) -> Unit) {
        //check here permission for location if permission is granted then proceed further. otherwise show toast
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    onSuccess(location)
                }
            } }


    @Suppress("DEPRECATION")
    fun getCityNameFromLocation(location:Location): String {
        var cityName = ""

        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude,location.longitude, 1)
            if (addresses != null) {
                    val city = addresses[0].locality
                    if (city != null) {
                        cityName = city
                    }
                }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        return cityName
    }
}


