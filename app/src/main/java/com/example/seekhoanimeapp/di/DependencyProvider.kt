package com.example.seekhoanimeapp.di

import android.content.Context

import com.example.seekhoanimeapp.data.repository.AnimeDetailRepository
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.data.network.ApiClient
import com.example.seekhoanimeapp.data.network.ApiService

object DependencyProvider {

    private fun provideApiService(): ApiService {
        return ApiClient.apiService
    }
//    private fun provideAnimeDatabase(context: Context): AnimeDatabase {
//        return AnimeDatabase.getInstance(context)
//    }
//    private fun provideAnimeDao(context: Context): AnimeDao {
//        return provideAnimeDatabase(context).animeDao()
//    }


    fun provideAnimeListRepository(context: Context): AnimeListRepository {
        return AnimeListRepository(provideApiService())
    }

    fun provideAnimeDetailRepository(): AnimeDetailRepository {
        return AnimeDetailRepository(provideApiService())
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(
            provideAnimeListRepository(context),
            provideAnimeDetailRepository()
        )
    }
}
