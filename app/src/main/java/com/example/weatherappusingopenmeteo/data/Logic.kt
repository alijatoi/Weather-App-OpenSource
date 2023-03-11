package com.example.weatherappusingopenmeteo.data

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.data.remote.WeatherData
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
class Logic {

    @SuppressLint("NewApi")
    fun currentDataFilter(currentWeather: WeatherData.CurrentWeather): CurrentWeatherEntity {
        return setCurrent(
            currentWeather.temperature, currentWeather.time,
            currentWeather.weathercode, currentWeather.winddirection, currentWeather.windspeed
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCurrent(temperature: Double, time: String, weathercode: Int, winddirection: Double, windspeed: Double): CurrentWeatherEntity {
        var formattedTime = ""
        val timeString = time.substring(11)
        if(timeString!=null){
        val time = LocalTime.parse(timeString)
        formattedTime = time.format(DateTimeFormatter.ofPattern("h:mm a"))
        }

        return CurrentWeatherEntity(temperature,formattedTime,weathercode,winddirection,windspeed)
    }


    fun hourlyDataFilter(hourly: WeatherData.Hourly, currentWeather: WeatherData.CurrentWeather): List<HourlyWeatherEntity> {

        val hourlyList = setHourly(
            hourly.time,
            hourly.temperature2m,
            hourly.weathercode,
            hourly.rain,
            hourly.snowfall
        )
        val latestHourly = setLatestHourly(hourlyList, currentWeather)
        return setHourlyDatabase(latestHourly)

    }


    fun dailyDataFilter(daily : WeatherData.Daily): List<DailyWeatherEntity> {
        val dailyList =  setDaily(daily.time,daily.temperature2mMax,daily.temperature2mMin,daily.weathercode)
        val latestDaily = setLatestDaily(dailyList)
        val dailyWeatherList = setWeeklyDatbase(latestDaily)
        return dailyWeatherList
    }

    private fun setHourly(time: List<String>, temperature2m: List<Double>, weathercode: List<Int>, rain: List<Double>, snow: List<Double>): List<List<Any>> {

        val result = listOf(time,temperature2m,weathercode,rain,snow)
        val output = result.first().indices.map { index ->
            result.map { it[index] }
        }

        return output
    }



    private fun setLatestHourly(hourlyList: List<List<Any>>, currentWeather: WeatherData.CurrentWeather
    ): List<List<Any>> {
        val startIndex = hourlyList.indexOfFirst { it.contains(currentWeather.time) } + 1
        val afterCurrentList = if (startIndex < hourlyList.size) {
            hourlyList.subList(startIndex, hourlyList.size)
        } else {
            emptyList()
        }
        Log.d("now",afterCurrentList.toString())
        return listOf(
            afterCurrentList[3],
            afterCurrentList[8],
            afterCurrentList[11],
            afterCurrentList[17]
        )
    }

    private fun setHourlyDatabase(latestHourly: List<List<Any>>): List<HourlyWeatherEntity> {
        val hourlyWeatherList = latestHourly.map {
            //Converting 24 hour to 12 Hour ( AM and PM)
            var formattedTime = ""
            val timeString = it[0].toString().substring(11)
            if(timeString!=null){
                val time = LocalTime.parse(timeString)
                formattedTime = time.format(DateTimeFormatter.ofPattern("h:mm a"))}

            HourlyWeatherEntity(
                formattedTime,
                temperature2m = it[1] as Double,
                weathercode = it[2] as Int,
                rain = it[3] as Double,
                snow = it[4] as Double
            )
        }
            return hourlyWeatherList

    }


    private fun setDaily(time: List<String>, temperature2mMax: List<Double>, temperature2mMin: List<Double>, weathercode: List<Int>): List<List<Any>> {

        val result = listOf(time,temperature2mMax,temperature2mMin,weathercode)
        val output = result.first().indices.map { index ->
            result.map { it[index] }
        }
        return output
    }

    private fun setLatestDaily(dailyList: List<List<Any>>): List<List<Any>> {
        return listOf(dailyList[2], dailyList[3], dailyList[4])
    }

    private fun setWeeklyDatbase(latestDaily: List<List<Any>>): List<DailyWeatherEntity> {
        val dailyWeatherList = latestDaily.map {
            DailyWeatherEntity(
                time = it[0] as String,
                temperature2mMax = it[1] as Double,
                temperature2mMin = it[2] as Double,
                weatheCode = it[3] as Int
            )
        }
        return dailyWeatherList
    }
}