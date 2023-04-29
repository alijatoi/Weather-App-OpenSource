package com.example.weatherappusingopenmeteo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v1/forecast")
    suspend fun getWeather(@Query("latitude") latitude:String?,
                           @Query("longitude") longitude:String?,
                           @Query("hourly", encoded = true) hour :String?,
                           @Query("daily", encoded = true) daily :String,
                           @Query("current_weather") boolen : String?,
                           @Query("timezone") timezone:String?) : Response<WeatherData>

}

