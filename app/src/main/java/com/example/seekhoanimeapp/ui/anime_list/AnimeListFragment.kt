package com.example.seekhoanimeapp.ui.anime_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seekhoanimeapp.R
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDto
import com.example.seekhoanimeapp.di.DependencyProvider
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.launch

class AnimeListFragment : Fragment() {

    private lateinit var viewModel: AnimeListViewModel
    private lateinit var adapter: AnimeListAdapter

    private lateinit var rvAnime: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var btnRetry: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAnime = view.findViewById(R.id.rvAnime)
        progressBar = view.findViewById(R.id.progressBar)
        tvError = view.findViewById(R.id.tvError)
        btnRetry = view.findViewById(R.id.btnRetry)

        rvAnime.layoutManager = LinearLayoutManager(requireContext())

        adapter = AnimeListAdapter(emptyList()) { anime ->
            val bundle = Bundle().apply {
                putInt("animeId", anime.animeId)
            }
            findNavController().navigate(R.id.action_list_to_detail, bundle)
        }

        rvAnime.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            DependencyProvider.provideViewModelFactory(-1 ,this.requireContext())
        )[AnimeListViewModel::class.java]
        observeAnimeList()
        btnRetry.setOnClickListener {
            viewModel.retry()
        }

    }

    private fun observeAnimeList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            showLoading(progressBar, rvAnime, tvError, btnRetry)
                        }

                        is UiState.Success -> {
                            adapter.updateData(state.data)
                            showContent(progressBar, rvAnime, tvError, btnRetry)
                        }

                        is UiState.Error -> {
                            tvError.text = state.message
                            showError(progressBar, rvAnime, tvError, btnRetry)
                        }
                    }
                }
            }
        }
    }
    private fun showLoading(
        progressBar: ProgressBar,
        rvAnime: RecyclerView,
        tvError: TextView,
        btnRetry: Button
    ) {
        progressBar.isVisible = true
        rvAnime.isVisible = false
        tvError.isVisible = false
        btnRetry.isVisible = false
    }

    private fun showContent(
        progressBar: ProgressBar,
        rvAnime: RecyclerView,
        tvError: TextView,
        btnRetry: Button
    ) {
        progressBar.isVisible = false
        rvAnime.isVisible = true
        tvError.isVisible = false
        btnRetry.isVisible = false
    }

    private fun showError(
        progressBar: ProgressBar,
        rvAnime: RecyclerView,
        tvError: TextView,
        btnRetry: Button
    ) {
        progressBar.isVisible = false
        rvAnime.isVisible = false
        tvError.isVisible = true
        btnRetry.isVisible = true
    }



}
