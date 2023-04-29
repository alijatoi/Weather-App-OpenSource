package com.example.weatherappusingopenmeteo.domain

import android.content.Context
import android.location.Location
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.weatherappusingopenmeteo.data.Logic
import com.example.weatherappusingopenmeteo.data.local.database.AppDatabase
import com.example.weatherappusingopenmeteo.data.local.database.CurrentCity
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.ApiResponse
import com.example.weatherappusingopenmeteo.data.remote.Remote
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(@ApplicationContext application : Context, private val remote: Remote) {

    //DataStore Database for CITY NAME
    private val currentCity: CurrentCity by lazy {
        CurrentCity(application)
    }

    private val database = AppDatabase.getDatabase(application)
    private val _error = MutableLiveData<String>()

        val cityName: Flow<String>
        get() = currentCity.getData.flowOn(Dispatchers.IO)

    val currentWeather: Flow<CurrentWeatherEntity?>
        get() = database.currentWeatherDAO().getCurrent().flowOn(Dispatchers.IO)
    val hourlyWeather: Flow<List<HourlyWeatherEntity?>>
        get() = database.hourlyWeatherDAO().getHourly().flowOn(Dispatchers.IO)
    val dailyWeather: Flow<List<DailyWeatherEntity?>>
        get() = database.dailyWeatherDAO().get().flowOn(Dispatchers.IO)
    val cityWeather = MutableLiveData<CurrentWeatherEntity>()


    suspend fun updateWeather(latitude: Double, longitude : Double) {
        when (val result = remote.getLatestUpdate(latitude,longitude)) {

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

    suspend fun getCityData(latitude: Double, longitude : Double){
        when (val result = remote.getLatestUpdate(latitude,longitude)) {
            is ApiResponse.Success -> {
                val weather = result.data
                val current = Logic().currentDataFilter(weather.currentWeather)
                cityWeather.postValue(current)
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

    suspend fun saveCity(cityName: String) {
            currentCity.saveData(cityName)
        }
}
