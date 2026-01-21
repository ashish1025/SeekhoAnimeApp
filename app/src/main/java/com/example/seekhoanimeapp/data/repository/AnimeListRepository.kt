package com.example.seekhoanimeapp.data.repository

import android.util.Log
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDto
import com.example.seekhoanimeapp.data.network.ApiService
import kotlinx.coroutines.flow.Flow

class AnimeListRepository(
    private val apiService: ApiService) {
    private val TAG = "AnimeListRepository"

//    fun getAnimeFromDb(): Flow<List<AnimeEntity>> {
//        return animeDao.observeAnimeList()
//    }

    suspend fun syncAnimeIfNeeded() {
        //if (animeDao.getAnimeCount() > 0) return

        val response = apiService.getTopAnime().data
        Log.d(TAG,"response: ${response.toString()}")

        val entityList = response.map {
            AnimeEntity(
                animeId = it.mal_id,
                title = it.title,
                imageUrl = it.images.jpg.image_url,
                rating = it.score?.toFloat() ?: 0f,
                episodes = it.episodes ?: 0
            )
        }

        //animeDao.insertAnimeList(entityList)
    }
}
