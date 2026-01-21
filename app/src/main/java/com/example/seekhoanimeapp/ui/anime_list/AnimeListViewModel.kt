package com.example.seekhoanimeapp.ui.anime_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDto
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.launch
class AnimeListViewModel(
    private val repository: AnimeListRepository
) : ViewModel() {
private val TAG = "AnimeListViewModel"
    private val _uiState = MutableLiveData<UiState<List<AnimeEntity>>>()
    val uiState: LiveData<UiState<List<AnimeEntity>>> = _uiState

    fun loadTopAnime() {
        Log.d(TAG, "loadTopAnime called")
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)

            try {
                val animeList = repository.syncAnimeIfNeeded()
                Log.d(TAG, "Anime list: $animeList")
                _uiState.postValue(UiState.Success(animeList as? List<AnimeEntity> ?: emptyList()))
            } catch (e: Exception) {
                _uiState.postValue(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}
