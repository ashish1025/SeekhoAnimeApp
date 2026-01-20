package com.example.seekhoanimeapp.di

import com.example.seekhoanimeapp.data.repository.AnimeDetailRepository
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.network.ApiClient
import com.example.seekhoanimeapp.network.ApiService

object DependencyProvider {

    private fun provideApiService(): ApiService {
        return ApiClient.apiService
    }

    fun provideAnimeListRepository(): AnimeListRepository {
        return AnimeListRepository(provideApiService())
    }

    fun provideAnimeDetailRepository(): AnimeDetailRepository {
        return AnimeDetailRepository(provideApiService())
    }

    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(
            provideAnimeListRepository(),
            provideAnimeDetailRepository()
        )
    }
}
