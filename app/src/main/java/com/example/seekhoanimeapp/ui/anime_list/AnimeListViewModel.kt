package com.example.seekhoanimeapp.ui.anime_list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.repository.AnimeListRepository
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class AnimeListViewModel(
    private val repository: AnimeListRepository
) : ViewModel() {
private val TAG = "AnimeListViewModel"

    private val _uiState =
        MutableStateFlow<UiState<List<AnimeEntity>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<AnimeEntity>>> =
        _uiState.asStateFlow()

    init {
        observeAnime()
        syncIfNeeded()
    }

    private fun observeAnime() {
        viewModelScope.launch {
            repository.getAnimeList().collect { list ->
                _uiState.value = UiState.Success(list)
            }
        }
    }



    private fun syncIfNeeded() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                repository.syncAnimeIfNeeded()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    message = e.message ?: "Something went wrong",
                    throwable = e
                )
            }
        }
    }

    fun retry() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                repository.forceSync()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    message = e.message ?: "Something went wrong",
                    throwable = e
                )
            }
        }
    }
}
