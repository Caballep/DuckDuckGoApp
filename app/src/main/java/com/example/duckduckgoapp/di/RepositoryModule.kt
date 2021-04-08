package com.example.duckduckgoapp.di

import com.example.duckduckgoapp.network.DuckDuckService
import com.example.duckduckgoapp.repositories.DuckDuckServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoriesModule {
    
    @Singleton
    @Provides
    fun providesDuckDuckServiceRepository(duckDuckService: DuckDuckService) =
        DuckDuckServiceRepository(duckDuckService)
}