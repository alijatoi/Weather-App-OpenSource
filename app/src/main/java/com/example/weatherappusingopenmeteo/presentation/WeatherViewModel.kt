package com.example.weatherappusingopenmeteo.presentation

import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappusingopenmeteo.domain.Repository
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.WeatherLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application):ViewModel() {

    private val _loadingState = MutableLiveData<WeatherLoadingState>()
    val loadingState: LiveData<WeatherLoadingState> = _loadingState
    private var restaurantRepository = Repository(application.applicationContext)

    val currentWeather: LiveData<CurrentWeatherEntity?>
        get() = restaurantRepository.currentWeather
    val hourlyWeather: LiveData<List<HourlyWeatherEntity?>>
        get() = restaurantRepository.hourlyWeather
    val dailyWeather: LiveData<List<DailyWeatherEntity?>>
        get() = restaurantRepository.dailyWeather


    fun updateWeather(location: Location) {
        try {
            viewModelScope.launch(Dispatchers.IO)
                {
                restaurantRepository.update(location)
                }
        }

            catch (e: Exception) {
                Log.d("error","inside exception")

            }
        }
    }

