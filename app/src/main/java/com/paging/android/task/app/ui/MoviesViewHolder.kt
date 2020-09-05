package com.paging.android.task.app.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.paging.android.task.app.BR
import com.paging.android.task.app.R
import com.paging.android.task.app.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_view_item.view.*

class MoviesViewHolder constructor(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {

    private var movie: Movie? = null

    fun bind(movie: Movie?) {

        if (movie != null) {
            showRepoData(movie)
        }

    }
    val avatarImage = itemView.ivUserProfile
    val releaseDate = itemView.date
    private fun showRepoData(movie: Movie) {
        dataBinding.setVariable(BR.item, movie)
        dataBinding.executePendingBindings()
        var date = movie.releaseDate
        if (date != null) {
            date = date.substring(0,4)
            releaseDate.setText(date)
        }
        Picasso.get().load("http://image.tmdb.org/t/p/w342/"+movie.posterPath).placeholder(R.drawable.loader).into(avatarImage);
    }
}
