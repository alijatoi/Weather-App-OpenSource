package com.example.weatherappusingopenmeteo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentWeather")
data class CurrentWeatherEntity( val temperature: Double,
                                 @PrimaryKey
                                 val time:String,
                                 val weathercode: Int,
                                 val winddirection: Double,
                                 val windspeed: Double  )

