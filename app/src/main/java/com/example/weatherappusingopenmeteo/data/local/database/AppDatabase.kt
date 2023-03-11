package com.example.weatherappusingopenmeteo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity


@Database(entities = [HourlyWeatherEntity::class, DailyWeatherEntity::class, CurrentWeatherEntity::class], version = 6)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currentWeatherDAO() : currentWeatherDAO
    abstract fun hourlyWeatherDAO() : hourlyWeatherDAO
    abstract fun dailyWeatherDAO() : dailyWeatherDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}


