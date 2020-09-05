package com.paging.android.task.app.api

import com.paging.android.task.app.model.Movie

data class MovieSearchResponse (
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<Movie> = emptyList(),
    val totalResults: Int? = null
)