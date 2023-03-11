package com.example.weatherappusingopenmeteo.data.remote

import android.location.Location
import com.example.weatherappusingopenmeteo.utils.CONSTANTS
import okio.IOException

class Remote   {
    val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
    suspend fun getLatestUpdate(location: Location): ApiResponse<WeatherData> {
            return try {

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
    }