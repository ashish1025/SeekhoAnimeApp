package com.example.seekhoanimeapp.data.dto

data class AnimeDetailResponse(
    val data: AnimeDetailDto
)

data class AnimeDetailDto(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<GenreDto>,
    val images: ImagesDto,
    val trailer: TrailerDto?,
    val characters: CharactersResponse?
)

data class TrailerDto(
    val youtube_id: String?,
    val url: String?
)

data class GenreDto(
    val name: String
)

data class CharactersResponse(
    val data: List<CharacterDto>
)

data class CharacterDto(
    val name: String,
    val role: String
)
