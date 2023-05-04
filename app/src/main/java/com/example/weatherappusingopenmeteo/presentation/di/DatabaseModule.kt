package com.example.weatherappusingopenmeteo.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.weatherappusingopenmeteo.data.local.database.AppDatabase
import com.example.weatherappusingopenmeteo.data.local.database.currentWeatherDAO
import com.example.weatherappusingopenmeteo.data.local.database.dailyWeatherDAO
import com.example.weatherappusingopenmeteo.data.local.database.hourlyWeatherDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideCurrentWeatherDAO(appDatabase: AppDatabase): currentWeatherDAO {
        return appDatabase.currentWeatherDAO()
    }

    @Provides
    fun provideHourlyWeatherDao(appDatabase: AppDatabase): hourlyWeatherDAO {
        return appDatabase.hourlyWeatherDAO()
    }


    @Provides
    fun provideDailyWeatherDAO(appDatabase: AppDatabase): dailyWeatherDAO {
        return appDatabase.dailyWeatherDAO()
    }

}