package com.paging.android.task.app.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.paging.android.task.app.data.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MoviesActivityViewModel constructor(private val repository: MovieRepository) : ViewModel() {
    val imageData = Pager(PagingConfig(pageSize = 20),pagingSourceFactory = {repository}).flow.cachedIn(viewModelScope)
    }
