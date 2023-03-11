package com.example.weatherappusingopenmeteo.domain

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.weatherappusingopenmeteo.data.Logic
import com.example.weatherappusingopenmeteo.data.local.database.AppDatabase
import com.example.weatherappusingopenmeteo.data.local.database.currentWeatherDAO
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.ApiResponse
import com.example.weatherappusingopenmeteo.data.remote.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val _error = MutableLiveData<String>()


    val currentWeather: LiveData<CurrentWeatherEntity?>
        get() = database.currentWeatherDAO().getCurrent().asLiveData()
    val hourlyWeather: LiveData<List<HourlyWeatherEntity?>>
        get() = database.hourlyWeatherDAO().getHourly().asLiveData()
    val dailyWeather: LiveData<List<DailyWeatherEntity?>>
        get() = database.dailyWeatherDAO().get().asLiveData()


    suspend fun update(location: Location) {

        when (val result = Remote().getLatestUpdate(location)) {

            is ApiResponse.Success -> {
                val weather = result.data
                val daily = Logic().dailyDataFilter(weather.daily)
                val current = Logic().currentDataFilter(weather.currentWeather)
                val hourly = Logic().hourlyDataFilter(weather.hourly, weather.currentWeather)
                insertData(current, hourly, daily)
            }
            is ApiResponse.Error -> {
                val exception = result.exception
                _error.postValue(exception.toString())
            }
        }
    }

    private suspend fun insertData(current: CurrentWeatherEntity, hourly: List<HourlyWeatherEntity>,
                                   daily: List<DailyWeatherEntity>) {
        withContext(Dispatchers.IO) {
            database.currentWeatherDAO().deleteCurrent()
            database.dailyWeatherDAO().delete()
            database.hourlyWeatherDAO().deleteHourly()

            database.currentWeatherDAO().insertCurrent(current)
            database.hourlyWeatherDAO().insertHourly(hourly)
            database.dailyWeatherDAO().insert(daily)
        }
    }
}