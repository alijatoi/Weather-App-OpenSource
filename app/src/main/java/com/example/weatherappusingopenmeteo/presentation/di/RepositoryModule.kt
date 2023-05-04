package com.example.weatherappusingopenmeteo.presentation.di

import com.example.weatherappusingopenmeteo.data.RepositoryImplementation
import com.example.weatherappusingopenmeteo.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideRepository(repository: RepositoryImplementation): Repository
}