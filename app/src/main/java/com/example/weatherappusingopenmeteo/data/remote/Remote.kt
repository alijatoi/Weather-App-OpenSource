package com.example.weatherappusingopenmeteo.data.remote

import android.location.Location
import android.util.Log
import com.example.weatherappusingopenmeteo.utils.CONSTANTS
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Remote @Inject constructor(private val apiInterface: ApiInterface)  {
    suspend fun getLatestUpdate(latitude: Double, longitude : Double): ApiResponse<WeatherData> {
            return try {
                val response = apiInterface.getWeather(latitude.toString(), longitude.toString(),
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