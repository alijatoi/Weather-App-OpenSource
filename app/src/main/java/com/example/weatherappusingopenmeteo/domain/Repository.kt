package com.example.weatherappusingopenmeteo.domain

import android.content.Context
import android.location.Location
import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class Repository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val _error = MutableLiveData<String>()


    val currentWeather: Flow<CurrentWeatherEntity?>
        get() = database.currentWeatherDAO().getCurrent().flowOn(Dispatchers.IO)
    val hourlyWeather: Flow<List<HourlyWeatherEntity?>>
        get() = database.hourlyWeatherDAO().getHourly().flowOn(Dispatchers.IO)
    val dailyWeather: Flow<List<DailyWeatherEntity?>>
        get() = database.dailyWeatherDAO().get().flowOn(Dispatchers.IO)


    suspend fun update(location: Location) {
//        val result = Remote().getCityUpdate("Hamburg")
//        Log.d("Chaa",result.toString())

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


    suspend fun getCityData(cityName:String){
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