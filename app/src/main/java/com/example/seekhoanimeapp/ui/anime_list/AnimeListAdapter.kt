package com.example.seekhoanimeapp.ui.anime_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seekhoanimeapp.R
import com.example.seekhoanimeapp.data.dto.AnimeDto
import com.example.seekhoanimeapp.databinding.ItemAnimeBinding
class AnimeListAdapter(
    private var animeList: List<AnimeDto>,
    private val onItemClick: (AnimeDto) -> Unit
) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: AnimeDto) {
            binding.tvTitle.text = anime.title
            binding.tvEpisodes.text = "Episodes: ${anime.episodes ?: "N/A"}"
            binding.tvRating.text = "⭐ ${anime.score ?: "N/A"}"

            Glide.with(binding.ivPoster.context)
                .load(anime.images.jpg.image_url)
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    fun updateData(newList: List<AnimeDto>) {
        animeList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size
}
