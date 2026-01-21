package com.example.seekhoanimeapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seekhoanimeapp.data.repository.AnimeDetailRepository
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.ui.anime_details.AnimeDetailViewModel
import com.example.seekhoanimeapp.ui.anime_list.AnimeListViewModel

class ViewModelFactory(
    private val animeListRepository: AnimeListRepository,
    private val animeDetailRepository: AnimeDetailRepository,
    private val animeId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnimeListViewModel::class.java) -> {
                AnimeListViewModel(animeListRepository) as T
            }

            modelClass.isAssignableFrom(AnimeDetailViewModel::class.java) -> {
                AnimeDetailViewModel(animeId,animeDetailRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


