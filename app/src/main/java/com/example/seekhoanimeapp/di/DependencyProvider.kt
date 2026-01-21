package com.example.seekhoanimeapp.di

import android.content.Context
import com.example.seekhoanimeapp.data.local.db.AnimeDao
import com.example.seekhoanimeapp.data.local.db.AppDatabase
import com.example.seekhoanimeapp.data.local.db.DatabaseProvider

import com.example.seekhoanimeapp.data.repository.AnimeDetailRepository
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.data.network.ApiClient
import com.example.seekhoanimeapp.data.network.ApiService

object DependencyProvider {

    private fun provideApiService(): ApiService {
        return ApiClient.apiService
    }
    private fun provideAnimeDatabase(context: Context): AppDatabase {
        return DatabaseProvider.getDatabase(context)
    }
    private fun provideAnimeDao(context: Context): AnimeDao {
        return provideAnimeDatabase(context).animeDao()
    }


    fun provideAnimeListRepository(context: Context): AnimeListRepository {
        return AnimeListRepository(provideAnimeDao(context),provideApiService())
    }

    fun provideAnimeDetailRepository(context: Context): AnimeDetailRepository {
        return AnimeDetailRepository(provideAnimeDao(context),provideApiService())
    }

    fun provideViewModelFactory(animeId: Int,context: Context): ViewModelFactory {
        return ViewModelFactory(
            provideAnimeListRepository(context),
            provideAnimeDetailRepository(context),
            animeId
        )
    }
}
