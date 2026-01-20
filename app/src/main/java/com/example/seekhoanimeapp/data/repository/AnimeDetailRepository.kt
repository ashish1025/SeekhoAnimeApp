package com.example.seekhoanimeapp.data.repository

import com.example.seekhoanimeapp.data.dto.AnimeDetailResponse
import com.example.seekhoanimeapp.network.ApiService

class AnimeDetailRepository(private val apiService: ApiService) {

    suspend fun getAnimeDetail(animeId: Int): AnimeDetailResponse {
        return apiService.getAnimeDetail(animeId)
    }
}
