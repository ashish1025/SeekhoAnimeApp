package com.example.seekhoanimeapp.data.repository

import com.example.seekhoanimeapp.data.dto.AnimeDto
import com.example.seekhoanimeapp.network.ApiService

class AnimeListRepository(private val apiService: ApiService) {
    suspend fun fetchTopAnime(): List<AnimeDto> {
        return apiService.getTopAnime().data
    }

}