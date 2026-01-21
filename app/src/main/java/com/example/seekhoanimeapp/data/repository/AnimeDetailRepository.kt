package com.example.seekhoanimeapp.data.repository

import com.example.seekhoanimeapp.data.local.db.AnimeDao
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDetailResponse
import com.example.seekhoanimeapp.data.network.ApiService
import kotlinx.coroutines.flow.Flow

class AnimeDetailRepository(
    private val animeDao: AnimeDao,
    private val apiService: ApiService) {


     fun getAnimeDetails(id: Int): Flow<AnimeEntity?> =
        animeDao.getAnimeById(id)

    suspend fun syncAnimeDetailsIfNeeded(id: Int) {
        val anime = animeDao.getAnimeByIdOnce(id)

        if (anime?.isDetailsFetched == true) return

        val response = apiService.getAnimeDetail(id).data

        val updated = anime?.copy(
            synopsis = response.synopsis,
            genres = response.genres.joinToString(","),
            imageUrl = response.images.jpg.image_url,
            trailerUrl = response.trailer?.url,
            isDetailsFetched = true
        )

        updated?.let { animeDao.insertAnime(it) }
    }
}
