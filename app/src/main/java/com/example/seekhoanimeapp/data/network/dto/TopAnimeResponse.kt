package com.example.seekhoanimeapp.data.network.dto

data class TopAnimeResponse(
val data: List<AnimeDto>
)

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: ImagesDto
)

data class ImagesDto(
    val jpg: JpgDto
)

data class JpgDto(
    val image_url: String
)