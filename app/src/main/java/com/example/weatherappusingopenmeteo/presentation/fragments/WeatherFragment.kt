package com.example.weatherappusingopenmeteo.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappusingopenmeteo.WeatherAdapter
import com.example.weatherappusingopenmeteo.domain.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.WeatherLoadingState
import com.example.weatherappusingopenmeteo.databinding.FragmentWeatherBinding
import com.example.weatherappusingopenmeteo.domain.utils.WeatherType
import com.example.weatherappusingopenmeteo.presentation.WeatherViewModel
import com.example.weatherappusingopenmeteo.domain.utils.LocationFetcher
import com.example.weatherappusingopenmeteo.domain.utils.NetworkUtils
import com.example.weatherappusingopenmeteo.domain.utils.PermissionCheck
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment() {


    private lateinit var viewBinding : FragmentWeatherBinding

    @Inject
    lateinit var permissionCheck: PermissionCheck
    @Inject
    lateinit var locationFetcher: LocationFetcher
    @Inject
    lateinit var checkInternet : NetworkUtils
    @Inject
    lateinit var adapter : WeatherAdapter

    lateinit var weatherViewModel : WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        viewBinding = FragmentWeatherBinding.inflate(inflater, container, false)

        viewBinding.recyclerviewHorizontal.adapter = adapter

        viewBinding.animationView.visibility = View.VISIBLE

        weatherViewModel.currentWeather.observe(viewLifecycleOwner) {
            setCurrentDisplay(it)
        }

        weatherViewModel.hourlyWeather.observe(viewLifecycleOwner) {
            adapter.setHourly(it)
        }

        weatherViewModel.dailyWeather.observe(viewLifecycleOwner) {
            viewBinding.animationView.visibility = View.GONE
            adapter.setDaily(it)
        }

        weatherViewModel.cityName.observe(viewLifecycleOwner){
            viewBinding.cityName.text = it.toString()
        }


        viewBinding.button.setOnClickListener {
            updateData()
        }


        weatherViewModel.loadingState.observe(viewLifecycleOwner) { loadingState ->
            when (loadingState) {
                is WeatherLoadingState.Error -> {
                }
                else -> {}
            }
        }
        return viewBinding.root
    }


    private fun updateData() {
        viewBinding.animationView.visibility = View.VISIBLE
        if (permissionCheck.requestLocationPermission()) {
           if (checkInternet.isNetworkConnected()) {
                locationFetcher.getCurrentLocation { location ->
                    if (location != null) {
                        val cityName = locationFetcher.getCityNameFromLocation(location)
                        weatherViewModel.saveCity(cityName)
                        weatherViewModel.getCurrentWeather(location.latitude,location.longitude)
                    }
                }
            }
            else {
                showPopup("Connect Internet","Please Connect Internet To Update The Weather")
            }
        }
        else{
        showPopup("Location On","Please Turn on Location to Update the Weather",)
    }
    }

    private fun setCurrentDisplay(current: CurrentWeatherEntity?) {
        if (current != null) {
            val details = WeatherType.fromWMO(current.weathercode)
            viewBinding.imageView.setImageResource(details.iconRes)
            viewBinding.ss.text = details.weatherDesc
            viewBinding.textView.text = "${current.temperature} °C"
            viewBinding.timeText.text = current.time
            viewBinding.windSpeedText.text = " ${current.windspeed} kmh"
            viewBinding.windDirectionText.text = "${current.winddirection} °"

        }
    }

    private fun showPopup(title: String, message : String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }
}