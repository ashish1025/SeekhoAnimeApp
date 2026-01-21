package com.example.seekhoanimeapp.ui.anime_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seekhoanimeapp.R
import com.example.seekhoanimeapp.data.local.entity.AnimeEntity
import com.example.seekhoanimeapp.data.network.dto.AnimeDto
import com.example.seekhoanimeapp.di.DependencyProvider
import com.example.seekhoanimeapp.utils.UiState

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
            hideListUI()
            val bundle = Bundle().apply {
                putInt("animeId", anime.animeId)
            }
            findNavController().navigate(R.id.action_list_to_detail, bundle)
        }

        rvAnime.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            DependencyProvider.provideViewModelFactory(this.requireContext())
        )[AnimeListViewModel::class.java]

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success<*> -> {
                    showList()
                    @Suppress("UNCHECKED_CAST")
                    adapter.updateData(state.data as List<AnimeEntity>)
                }
                is UiState.Error -> showError(state.message)
            }
        }

        btnRetry.setOnClickListener {
            viewModel.loadTopAnime()
        }

        viewModel.loadTopAnime()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        rvAnime.visibility = View.GONE
        tvError.visibility = View.GONE
        btnRetry.visibility = View.GONE
    }

    private fun showList() {
        progressBar.visibility = View.GONE
        rvAnime.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        btnRetry.visibility = View.GONE
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        rvAnime.visibility = View.GONE
        tvError.visibility = View.VISIBLE
        btnRetry.visibility = View.VISIBLE
        tvError.text = message
    }

    private fun hideListUI() {
        rvAnime.visibility = View.GONE
        progressBar.visibility = View.GONE
        tvError.visibility = View.GONE
        btnRetry.visibility = View.GONE
        adapter.updateData(emptyList())
    }
}
