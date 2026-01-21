package com.example.seekhoanimeapp.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey
    val animeId: Int,
    val title: String? = null,
    val imageUrl: String? = null,
    val rating: Float? = null,
    val episodes: Int?  = null,

    val synopsis: String? = null,
    val genres: String? = null,
    val trailerUrl: String? = null,
    val duration: String? = null,

    val isDetailsFetched: Boolean = false
)

