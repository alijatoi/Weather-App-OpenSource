package com.example.weatherappusingopenmeteo.presentation

import com.example.weatherappusingopenmeteo.utils.LocationFetcher
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.weatherappusingopenmeteo.WeatherAdapter
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.WeatherLoadingState
import com.example.weatherappusingopenmeteo.databinding.ActivityMainBinding
import com.example.weatherappusingopenmeteo.utils.NetworkUtils
import com.example.weatherappusingopenmeteo.utils.PermissionCheck


class MainActivity : AppCompatActivity() {
    private lateinit var permissionCheck: PermissionCheck
    private lateinit var locationFetcher: LocationFetcher
    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels{WeatherViewModelFactory(application)}

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = WeatherAdapter()

        binding.recyclerviewHorizontal.adapter = adapter
        binding.animationView.visibility = View.VISIBLE



        weatherViewModel.currentWeather.observe(this) {
            setCurrentDisplay(it)
        }

        weatherViewModel.hourlyWeather.observe(this) {
            adapter.setHourly(it)

        }
        weatherViewModel.dailyWeather.observe(this) {
            binding.animationView.visibility = View.GONE
            adapter.setDaily(it)
        }


        binding.button.setOnClickListener {
            updateData()
        }


        weatherViewModel.loadingState.observe(this) { loadingState ->
            when (loadingState) {
                is WeatherLoadingState.Error -> {
                    showPopup("Error","Error updating the weather")
                }
                else -> {}
            }
        }
    }



    private fun updateData() {
        binding.animationView.visibility = View.VISIBLE
        permissionCheck = PermissionCheck(this)
        if (permissionCheck.requestLocationPermission()) {
            if (NetworkUtils.isNetworkConnected(this)) {
                locationFetcher = LocationFetcher(this)
                locationFetcher.getCurrentLocation { location ->
                    if (location != null) {
                        val cityName = locationFetcher.getCityNameFromLocation(location)
                        binding.cityName.text = cityName
                        weatherViewModel.updateWeather(location)
                    }
                }
            }
            else {
                showPopup("Connect Internet","Please Connect Internet To Update The Weather")
            }
        }
    }

    private fun setCurrentDisplay(current: CurrentWeatherEntity?) {
        if (current != null) {
            val details = WeatherType.fromWMO(current.weathercode)
            binding.imageView.setImageResource(details.iconRes)
            binding.ss.text = details.weatherDesc
            binding.textView.text = "${current.temperature} °C"
            binding.timeText.text = current.time
            binding.windSpeedText.text = " ${current.windspeed} kmh"
            binding.windDirectionText.text = "${current.winddirection} °"

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permissionCheck.LOCATION_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateData()
        } else {
            permissionCheck.showRationaleDialog()
        }
    }


    private fun showPopup(title: String, message : String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }
}



