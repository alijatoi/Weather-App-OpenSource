package com.example.weatherappusingopenmeteo.presentation.di

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.weatherappusingopenmeteo.domain.utils.LocationFetcher
import com.example.weatherappusingopenmeteo.domain.utils.PermissionCheck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun providePermissionCheck(activity: Activity, context: Context): PermissionCheck {
        return PermissionCheck(activity,context)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}