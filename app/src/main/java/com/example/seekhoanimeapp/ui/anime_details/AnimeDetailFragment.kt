package com.example.seekhoanimeapp.ui.anime_details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.seekhoanimeapp.R
import com.example.seekhoanimeapp.data.network.dto.AnimeDetailDto
import com.example.seekhoanimeapp.databinding.FragmentAnimeDetailBinding
import com.example.seekhoanimeapp.di.DependencyProvider
import com.example.seekhoanimeapp.utils.UiState
import androidx.core.net.toUri
import androidx.core.graphics.drawable.toDrawable

class AnimeDetailFragment : Fragment() {

    private val TAG = "AnimeDetailFragment"

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AnimeDetailViewModel

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

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

        val animeId = arguments?.getInt("animeId") ?: return

        viewModel = ViewModelProvider(
            this,
            DependencyProvider.provideViewModelFactory(this.requireContext())
        )[AnimeDetailViewModel::class.java]

        viewModel.loadAnimeDetail(animeId)
        observeAnimeDetails()


    }

    private fun observeAnimeDetails() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
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
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(data: AnimeDetailDto) {

        binding.tvTitle.text = data.title
        binding.tvSynopsis.text = data.synopsis ?: "No synopsis available"

        binding.tvGenres.text = data.genres.joinToString(", ") { it.name }

        val castText = data.characters?.data?.take(5)
            ?.joinToString(", ") { it.name } ?: "No cast available"

        binding.tvCast.text = castText

        binding.tvEpisodes.text = "Episodes: ${data.episodes ?: "NA"}"
        binding.tvRating.text = "Rating: ${data.score ?: "NA"}"
        val trailerUrl = data.trailer?.url
        val youtubeUrl = data.trailer?.youtube_id
        playerView = binding.playerView


        if (!trailerUrl.isNullOrEmpty()) {
            Log.d(TAG,"Trailer URL: $trailerUrl")
            binding.imgPoster.visibility = View.GONE
            playerView.visibility = View.VISIBLE
            initializePlayer(trailerUrl)
        }
        else  if (!youtubeUrl.isNullOrEmpty()) {
            binding.imgPoster.visibility = View.GONE
            binding.playerView.visibility = View.VISIBLE
            initializeYoutubePlayer(youtubeUrl)
        }
        else {
            playerView.visibility = View.GONE
            binding.imgPoster.visibility = View.VISIBLE
            Log.d(TAG,"poster available URL: ${data.images.jpg.image_url}")

            Glide.with(requireContext())
                .load(data.images.jpg.image_url)
                .placeholder(R.drawable.ic_anime_placeholder)
                .error(R.drawable.ic_anime_placeholder)
                .fallback(R.drawable.ic_anime_placeholder)
                .into(binding.imgPoster)

        }
    }

    private fun initializeYoutubePlayer(youtubeUrl: String) {
        binding.imgPoster.visibility = View.GONE
        binding.playerView.visibility = View.VISIBLE

        // Thumbnail URL
        val thumbnailUrl = "https://img.youtube.com/vi/$youtubeUrl/hqdefault.jpg"

        Glide.with(requireContext())
            .asBitmap()
            .load(thumbnailUrl)
            .placeholder(R.drawable.ic_anime_placeholder)
            .error(R.drawable.ic_anime_placeholder)
            .into(object : CustomTarget<Bitmap>() {
                @SuppressLint("UnsafeOptInUsageError")
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    binding.playerView.defaultArtwork =
                        resource.toDrawable(resources)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        binding.playerView.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                "https://www.youtube.com/watch?v=$youtubeUrl".toUri()
            )
            startActivity(intent)
        }
    }

    private fun initializePlayer(videoUrl: String) {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            playerView.player = exoPlayer

            val mediaItem = MediaItem.fromUri(videoUrl)
            exoPlayer.setMediaItem(mediaItem)

            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
        _binding = null
    }
}
