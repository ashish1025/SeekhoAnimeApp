package com.example.seekhoanimeapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seekhoanimeapp.di.ViewModelFactory
import com.example.seekhoanimeapp.ui.anime_list.AnimeListFragment
import com.example.seekhoanimeapp.ui.anime_list.AnimeListViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called ")

    }
/*

    com/yourname/seekhoanime
    ├── core
    │    ├── network
    │    │    ├── ApiClient.kt
    │    │    ├── NetworkHelper.kt
    │    │    └── RetrofitProvider.kt   (optional abstraction)
    │    └── utils
    │         └── Resource.kt / UiState.kt
    │
    ├── data
    │    ├── local
    │    │    ├── db
    │    │    │    ├── AnimeDatabase.kt
    │    │    │    ├── AnimeDao.kt
    │    │    │    └── entities
    │    │    │         ├── AnimeEntity.kt
    │    │    │         ├── GenreEntity.kt
    │    │    │         └── CastEntity.kt
    │    │
    │    ├── remote
    │    │    ├── JikanApiService.kt
    │    │    └── dto
    │    │         ├── AnimeDto.kt
    │    │         ├── TopAnimeResponse.kt
    │    │         └── AnimeDetailResponse.kt
    │    │
    │    └── repository
    │         └── AnimeRepository.kt
    │
    ├── ui
    │    ├── anime_list
    │    │    ├── AnimeListFragment.kt
    │    │    ├── AnimeListAdapter.kt
    │    │    └── AnimeListViewModel.kt
    │    │
    │    ├── anime_detail
    │    │    ├── AnimeDetailFragment.kt
    │    │    └── AnimeDetailViewModel.kt
    │    │
    │    └── MainActivity.kt
    │
    └── App.kt

 */
}