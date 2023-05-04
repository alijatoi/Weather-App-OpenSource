package com.example.weatherappusingopenmeteo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailyWeather")
data class DailyWeatherEntity(
@PrimaryKey
val time : String,
val temperature2mMax : Double,
val temperature2mMin : Double,
val weatheCode : Int
)


