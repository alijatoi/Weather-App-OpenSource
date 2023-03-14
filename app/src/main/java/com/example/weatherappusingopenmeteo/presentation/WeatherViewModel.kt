package com.example.weatherappusingopenmeteo.presentation

import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherappusingopenmeteo.domain.Repository
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.WeatherLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(@ApplicationContext application : Context):ViewModel() {


    private var restaurantRepository = Repository(application)

    private val _loadingState = MutableLiveData<WeatherLoadingState>()
    val loadingState: LiveData<WeatherLoadingState> = _loadingState

    val currentWeather: LiveData<CurrentWeatherEntity?>
        get() = restaurantRepository.currentWeather.asLiveData()
    val hourlyWeather: LiveData<List<HourlyWeatherEntity?>>
        get() = restaurantRepository.hourlyWeather.asLiveData()
    val dailyWeather: LiveData<List<DailyWeatherEntity?>>
        get() = restaurantRepository.dailyWeather.asLiveData()


    fun updateWeather(location: Location) {
        try {
            viewModelScope.launch(Dispatchers.IO)
                {
                restaurantRepository.update(location)
                }
        }

            catch (e: Exception) {
                _loadingState.value = WeatherLoadingState.Error
            }
        }
    }

