package com.example.seekhoanimeapp.data.repository

import com.example.seekhoanimeapp.data.network.dto.AnimeDetailResponse
import com.example.seekhoanimeapp.data.network.ApiService

class AnimeDetailRepository(private val apiService: ApiService) {

    suspend fun getAnimeDetail(animeId: Int): AnimeDetailResponse {
        return apiService.getAnimeDetail(animeId)
    }
}
