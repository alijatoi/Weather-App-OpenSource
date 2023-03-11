package com.example.weatherappusingopenmeteo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "hourlyWeather")
data class HourlyWeatherEntity(
    @PrimaryKey
    val time : String,
    val temperature2m : Double,
    val weathercode : Int,
    val rain : Double,
    val snow : Double
)
