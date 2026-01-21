package com.example.seekhoanimeapp.data.network

import com.example.seekhoanimeapp.data.network.dto.AnimeDetailResponse
import com.example.seekhoanimeapp.data.network.dto.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

@GET("v4/top/anime")
suspend fun getTopAnime(
    @Query("page") page: Int = 1
): TopAnimeResponse

@GET("v4/anime/{id}/full")
suspend fun getAnimeDetail(
    @Path("id") animeId: Int
): AnimeDetailResponse


}