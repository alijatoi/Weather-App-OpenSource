package com.example.weatherappusingopenmeteo.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.weatherappusingopenmeteo.data.local.database.*
import com.example.weatherappusingopenmeteo.domain.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.ApiResponse
import com.example.weatherappusingopenmeteo.data.remote.Remote
import com.example.weatherappusingopenmeteo.domain.Mapper
import com.example.weatherappusingopenmeteo.domain.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImplementation @Inject constructor(@ApplicationContext application : Context, private val remote: Remote,
                                                   private val currentWeatherDAO : currentWeatherDAO,
                                                   private val hourlyWeatherDAO: hourlyWeatherDAO,
                                                   private val dailyWeatherDAO : dailyWeatherDAO,
                                                   private val mapper: Mapper
): Repository {


    //DataStore Database for CITY NAME
    private val currentCity: CurrentCity by lazy {
        CurrentCity(application)
    }

    override val _error = MutableLiveData<String>()

    override val cityName: Flow<String>
        get() = currentCity.getData.flowOn(Dispatchers.IO)


    override val currentWeather: Flow<CurrentWeatherEntity?>
        get() = currentWeatherDAO.getCurrent().flowOn(Dispatchers.IO)
    override val hourlyWeather: Flow<List<HourlyWeatherEntity?>>
        get() = hourlyWeatherDAO.getHourly().flowOn(Dispatchers.IO)
    override val dailyWeather: Flow<List<DailyWeatherEntity?>>
        get() = dailyWeatherDAO .get().flowOn(Dispatchers.IO)
    override val cityWeather = MutableLiveData<CurrentWeatherEntity>()


        override suspend fun updateWeather(latitude: Double, longitude : Double) {
            when (val result = remote.getLatestUpdate(latitude,longitude)) {

            is ApiResponse.Success -> {
                val weather = result.data
                val daily =  mapper.dailyDataFilter(weather.daily)
                val current = mapper.currentDataFilter(weather.currentWeather)
                val hourly =  mapper.hourlyDataFilter(weather.hourly, weather.currentWeather)
                insertData(current, hourly, daily)
            }
            is ApiResponse.Error -> {
                val exception = result.exception
                _error.postValue(exception.toString())
            }
        }
    }

    override suspend fun getCityData(latitude: Double, longitude : Double){
        when (val result = remote.getLatestUpdate(latitude,longitude)) {
            is ApiResponse.Success -> {
                val weather = result.data
                val current = mapper.currentDataFilter(weather.currentWeather)
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
            currentWeatherDAO.deleteCurrent()
            dailyWeatherDAO.delete()
            hourlyWeatherDAO.deleteHourly()

            currentWeatherDAO.insertCurrent(current)
            hourlyWeatherDAO.insertHourly(hourly)
            dailyWeatherDAO.insert(daily)
        }
    }

    override suspend fun saveCity(cityName: String) {
            currentCity.saveData(cityName)
        }
}
