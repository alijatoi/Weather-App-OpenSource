package com.example.weatherappusingopenmeteo.data.remote


import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather,
    @SerializedName("daily")
    val daily: Daily,
    @SerializedName("daily_units")
    val dailyUnits: DailyUnits,
    @SerializedName("elevation")
    val elevation: Double,
    @SerializedName("generationtime_ms")
    val generationtimeMs: Double,
    @SerializedName("hourly")
    val hourly: Hourly,
    @SerializedName("hourly_units")
    val hourlyUnits: HourlyUnits,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int
) {
    data class CurrentWeather(
        @SerializedName("temperature")
        val temperature: Double,
        @SerializedName("time")
        val time: String,
        @SerializedName("weathercode")
        val weathercode: Int,
        @SerializedName("winddirection")
        val winddirection: Double,
        @SerializedName("windspeed")
        val windspeed: Double
    )

    data class Daily(
        @SerializedName("temperature_2m_max")
        val temperature2mMax: List<Double>,
        @SerializedName("temperature_2m_min")
        val temperature2mMin: List<Double>,
        @SerializedName("time")
        val time: List<String>,
        @SerializedName("weathercode")
        val weathercode: List<Int>
    )

    data class DailyUnits(
        @SerializedName("temperature_2m_max")
        val temperature2mMax: String,
        @SerializedName("temperature_2m_min")
        val temperature2mMin: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("weathercode")
        val weathercode: String
    )

    data class Hourly(
        @SerializedName("rain")
        val rain: List<Double>,
        @SerializedName("snowfall")
        val snowfall: List<Double>,
        @SerializedName("temperature_2m")
        val temperature2m: List<Double>,
        @SerializedName("time")
        val time: List<String>,
        @SerializedName("weathercode")
        val weathercode: List<Int>
    )

    data class HourlyUnits(
        @SerializedName("rain")
        val rain: String,
        @SerializedName("snowfall")
        val snowfall: String,
        @SerializedName("temperature_2m")
        val temperature2m: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("weathercode")
        val weathercode: String
    )
}