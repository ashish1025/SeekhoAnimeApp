package com.example.seekhoanimeapp.ui.anime_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoanimeapp.data.dto.AnimeDetailDto
import com.example.seekhoanimeapp.data.repository.AnimeDetailRepository
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repository: AnimeDetailRepository) : ViewModel() {

    private val TAG = "AnimeDetailViewModel"
    private val _uiState = MutableLiveData<UiState<AnimeDetailDto>>()
    val uiState: LiveData<UiState<AnimeDetailDto>> = _uiState

    fun loadAnimeDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getAnimeDetail(id)
                _uiState.value = UiState.Success(response.data)
                Log.d(TAG, "Anime detail: ${response.data.toString()}")
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
