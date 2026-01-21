package com.example.seekhoanimeapp.ui.anime_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDto
import com.example.seekhoanimeapp.databinding.ItemAnimeBinding
class AnimeListAdapter(
    private var animeList: List<AnimeEntity>,
    private val onItemClick: (AnimeEntity) -> Unit
) : RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: AnimeEntity) {
            binding.tvTitle.text = anime.title
            binding.tvEpisodes.text = "Episodes: ${anime.episodes ?: "N/A"}"
            binding.tvRating.text = "⭐ ${anime.rating ?: "N/A"}"

            Glide.with(binding.ivPoster.context)
                .load(anime.imageUrl)
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    fun updateData(newList: List<AnimeEntity>) {
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
