package com.example.seekhoanimeapp.ui.anime_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoanimeapp.data.dto.AnimeDto
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.launch
class AnimeListViewModel(
    private val repository: AnimeListRepository
) : ViewModel() {
private val TAG = "AnimeListViewModel"
    private val _uiState = MutableLiveData<UiState<List<AnimeDto>>>()
    val uiState: LiveData<UiState<List<AnimeDto>>> = _uiState

    fun loadTopAnime() {
        Log.d(TAG, "loadTopAnime called")
        viewModelScope.launch {
            _uiState.postValue(UiState.Loading)

            try {
                val animeList = repository.fetchTopAnime()
                Log.d(TAG, "Anime list: $animeList")
                _uiState.postValue(UiState.Success(animeList))
            } catch (e: Exception) {
                _uiState.postValue(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}
