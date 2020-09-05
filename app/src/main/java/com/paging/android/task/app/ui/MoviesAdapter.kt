package com.paging.android.task.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.paging.android.task.app.databinding.MovieViewItemBinding
import com.paging.android.task.app.model.Movie

class MoviesAdapter : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = MovieViewItemBinding.inflate(inflater, parent, false)
        return MoviesViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as MoviesViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem == newItem
        }
    }
}
