package com.example.weatherappusingopenmeteo.data.remote

import android.location.Location
import android.util.Log
import com.example.weatherappusingopenmeteo.utils.CONSTANTS
import okio.IOException

class Remote   {
    suspend fun getLatestUpdate(location: Location): ApiResponse<WeatherData> {
            return try {
                val apiInterface = ApiUtilities.getUpdateInstance().create(ApiInterface::class.java)

                val response = apiInterface.getWeather(location.latitude.toString(), location.longitude.toString(),
                    CONSTANTS.hourly,
                    CONSTANTS.daily,
                    CONSTANTS.currentWeather.toString(),
                    CONSTANTS.timezone)

                if (response.isSuccessful) {
                    ApiResponse.Success(response.body()!!)
                }
                else {
                    ApiResponse.Error(IOException(response.errorBody()?.string()))
                }
            }
            catch (e: Exception) {
                ApiResponse.Error(e)
            }
        }

     suspend fun getCityUpdate(cityName : String){
        val apiInterface = ApiUtilities.getCityInstance().create(ApiInterface::class.java)
        val response = apiInterface.getCityData(cityName)
        if (response.isSuccessful) {
            Log.d("Response",response.body().toString())
        }
    }
    }