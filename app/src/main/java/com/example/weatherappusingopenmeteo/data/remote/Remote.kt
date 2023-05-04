package com.example.weatherappusingopenmeteo.data.remote


import com.example.weatherappusingopenmeteo.data.remote.ApiInterface
import com.example.weatherappusingopenmeteo.data.remote.ApiResponse
import com.example.weatherappusingopenmeteo.data.remote.WeatherData
import com.example.weatherappusingopenmeteo.domain.utils.CONSTANTS
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