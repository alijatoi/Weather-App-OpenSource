package com.example.weatherappusingopenmeteo.presentation

import androidx.lifecycle.*
import com.example.weatherappusingopenmeteo.domain.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.WeatherLoadingState
import com.example.weatherappusingopenmeteo.domain.Repository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository : Repository):ViewModel() {

dfksdkfjdskfjdskfjfkdsfjdsk
    private val _loadingState = MutableLiveData<WeatherLoadingState>()
    val loadingState: LiveData<WeatherLoadingState> = _loadingState
zdfksdhfjkdshfjkdsf
sfsjdfhsdjfhdsjfsdfksdfhdsjkf
sfksjfsdkfjsdkfjsdkfjsdfksd
sdkfhsdjfsdfhsdfjsdfjds
sdfkhfdfhdjfdjf
    val currentWeather: LiveData<CurrentWeatherEntity?>
        get() = weatherRepository.currentWeather.asLiveData()
    val hourlyWeather: LiveData<List<HourlyWeatherEntity?>>
        get() = weatherRepository.hourlyWeather.asLiveData()
    val dailyWeather: LiveData<List<DailyWeatherEntity?>>
        get() = weatherRepository.dailyWeather.asLiveData()
    val cityWeather : LiveData<CurrentWeatherEntity>
    get() = weatherRepository.cityWeather
    نرنرکنارکنارکنار

    val cityName: LiveData<String>
        get() = weatherRepository.cityName.asLiveData()



    fun getCurrentWeather(latitude: Double, longitude:Double) {
        try {
            viewModelScope.launch(Dispatchers.IO)
                {
                weatherRepository.updateWeather(latitude,longitude)
                }
        }

            catch (e: Exception) {
                _loadingState.value = WeatherLoadingState.Error
            }
        }

    fun getCityWeather(location: LatLng) {
        try {
            viewModelScope.launch(Dispatchers.IO)
            {
                weatherRepository.getCityData(location.latitude,location.longitude)
            }
        }
        catch (e: Exception) {
            _loadingState.value = WeatherLoadingState.Error
        }
    }


    fun saveCity(cityName: String) {
        try{
            viewModelScope.launch (Dispatchers.IO){
            weatherRepository.saveCity(cityName)
    }
        }
        catch (e : Exception){

        }
    }
}

