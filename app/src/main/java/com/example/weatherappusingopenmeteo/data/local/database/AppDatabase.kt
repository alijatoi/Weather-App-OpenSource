package com.example.weatherappusingopenmeteo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherappusingopenmeteo.domain.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.domain.model.HourlyWeatherEntity


@Database(entities = [HourlyWeatherEntity::class, DailyWeatherEntity::class, CurrentWeatherEntity::class], version = 6)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currentWeatherDAO() : currentWeatherDAO
    abstract fun hourlyWeatherDAO() : hourlyWeatherDAO
    abstract fun dailyWeatherDAO() : dailyWeatherDAO

}


