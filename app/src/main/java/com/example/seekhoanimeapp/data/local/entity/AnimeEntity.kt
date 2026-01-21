package com.example.seekhoanimeapp.data.local.entity



data class AnimeEntity(
    val animeId: Int,

    val title: String,
    val imageUrl: String,
    val rating: Float,
    val episodes: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)
