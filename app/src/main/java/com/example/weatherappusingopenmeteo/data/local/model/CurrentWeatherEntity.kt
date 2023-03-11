package com.example.weatherappusingopenmeteo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentWeather")
data class CurrentWeatherEntity( val temperature: Double,
                                 @PrimaryKey
                                 val time:String,
                                 val weathercode: Int,
                                 val winddirection: Double,
                                 val windspeed: Double  )

