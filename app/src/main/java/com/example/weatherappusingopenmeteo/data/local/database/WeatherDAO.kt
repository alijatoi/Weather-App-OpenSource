package com.example.weatherappusingopenmeteo.data.local.database

import androidx.room.*
import com.example.weatherappusingopenmeteo.data.local.model.CurrentWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface currentWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrent(item: CurrentWeatherEntity)

    @Query("SELECT * FROM CurrentWeather")
    fun getCurrent(): Flow<CurrentWeatherEntity>

    @Query("DELETE FROM CurrentWeather")
    suspend fun deleteCurrent()
}


@Dao
interface hourlyWeatherDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourly(item : List<HourlyWeatherEntity>)


    @Query("SELECT * FROM hourlyWeather")
    fun getHourly () : Flow<List<HourlyWeatherEntity>>


    @Query ("DELETE FROM hourlyWeather")
    suspend fun deleteHourly()
}


@Dao
interface dailyWeatherDAO{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : List<DailyWeatherEntity>)


    @Query("SELECT * FROM dailyWeather")
    fun get () : Flow<List<DailyWeatherEntity>>


    @Query ("DELETE FROM dailyWeather")
    suspend fun delete()
}