package com.example.seekhoanimeapp.ui.anime_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDetailDto
import com.example.seekhoanimeapp.data.repository.AnimeDetailRepository
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val animeId: Int,
    private val repository: AnimeDetailRepository) : ViewModel() {

    private val TAG = "AnimeDetailViewModel"
    private val _uiState =
        MutableStateFlow<UiState<AnimeEntity>>(UiState.Loading)
    val uiState: StateFlow<UiState<AnimeEntity>> = _uiState

    init {
        observeDetails()
        syncDetails()
    }

    private fun observeDetails() {
        viewModelScope.launch {
            repository.getAnimeDetails(animeId).collect { anime ->
                anime?.let {
                    _uiState.value = UiState.Success(it)
                }
            }
        }
    }

    private fun syncDetails() {
        viewModelScope.launch {
            try {
                repository.syncAnimeDetailsIfNeeded(animeId)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Failed to load details",
                    e
                )
            }
        }
    }
}
