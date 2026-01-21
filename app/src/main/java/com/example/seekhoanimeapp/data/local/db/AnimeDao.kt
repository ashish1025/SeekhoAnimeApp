package com.example.seekhoanimeapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    fun getAllAnime(): Flow<List<AnimeEntity>>
    @Query("SELECT * FROM anime WHERE animeId = :id")
    fun getAnimeById(id: Int): Flow<AnimeEntity?>
    @Query("SELECT * FROM anime WHERE animeId = :id")
    suspend fun getAnimeByIdOnce(id: Int): AnimeEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(animeList: List<AnimeEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity)

    @Query("DELETE FROM anime")
    suspend fun clearAll()
    @Query("SELECT COUNT(*) FROM anime")
    suspend fun getAnimeCount(): Int

}
