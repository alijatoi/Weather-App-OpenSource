package com.example.weatherappusingopenmeteo.domain

import androidx.lifecycle.MutableLiveData
import com.example.weatherappusingopenmeteo.domain.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.HourlyWeatherEntity
import kotlinx.coroutines.flow.Flow

interface Repository {

    val cityName: Flow<String>

    val _error : MutableLiveData<String>

    val currentWeather: Flow<CurrentWeatherEntity?>

    val hourlyWeather: Flow<List<HourlyWeatherEntity?>>

    val dailyWeather: Flow<List<DailyWeatherEntity?>>

    val cityWeather : MutableLiveData<CurrentWeatherEntity>



    suspend fun updateWeather(latitude: Double, longitude : Double)

    suspend fun getCityData(latitude: Double, longitude : Double)


    suspend fun saveCity(cityName: String)
}
