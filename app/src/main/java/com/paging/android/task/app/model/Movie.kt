package com.paging.android.task.app.model

data class Movie (
        val overview: String? = null,
        val original_language: String? = null,
        val originalTitle: String? = null,
        val video: Boolean? = null,
        val title: String,
        val genreIds: List<Int?>? = null,
        val poster_path: String? = null,
        val backdropPath: String? = null,
        val release_date: String? = null,
        val popularity: Double? = null,
        val voteAverage: Double? = null,
        val id: Int? = null,
        val adult: Boolean? = null,
        val voteCount: Int? = null
)
