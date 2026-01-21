package com.example.seekhoanimeapp.ui.anime_details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.seekhoanimeapp.R
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.databinding.FragmentAnimeDetailBinding
import com.example.seekhoanimeapp.di.DependencyProvider
import com.example.seekhoanimeapp.utils.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AnimeDetailFragment : Fragment() {

    private val TAG = "AnimeDetailFragment"

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var viewModel: AnimeDetailViewModel



    private val animeId: Int by lazy {
        requireArguments().getInt("animeId")
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            DependencyProvider.provideViewModelFactory(animeId, this.requireContext())
        )[AnimeDetailViewModel::class.java]
        collectUiState()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        bindData(state.data)
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e(TAG, state.message, state.throwable)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(data: AnimeEntity) {

        binding.tvTitle.text = data.title
        binding.tvSynopsis.text = data.synopsis ?: "No synopsis available"
        binding.tvGenres.text = data.genres ?: "No genres available"
        binding.tvEpisodes.text = "Episodes: ${data.episodes ?: "NA"}"
        binding.tvRating.text = "Rating: ${data.rating ?: "NA"}"

        playerView = binding.playerView

        when {
            !data.trailerUrl.isNullOrEmpty() -> {
                binding.imgPoster.visibility = View.GONE
                playerView.visibility = View.VISIBLE
                initializePlayer(data.trailerUrl)
            }

            else -> {
                showPoster(data.imageUrl)
            }
        }
    }

    private fun showPoster(imageUrl: String?) {
        binding.playerView.visibility = View.GONE
        binding.imgPoster.visibility = View.VISIBLE

        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(R.drawable.ic_anime_placeholder)
            .error(R.drawable.ic_anime_placeholder)
            .into(binding.imgPoster)
    }


    private fun initializePlayer(videoUrl: String) {
        player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        player.setMediaItem(MediaItem.fromUri(videoUrl))
        player.prepare()
        player.playWhenReady = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::player.isInitialized) {
            player.release()
        }
        _binding = null
    }
}

