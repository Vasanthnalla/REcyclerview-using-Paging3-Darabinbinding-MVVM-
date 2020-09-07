package com.paging.android.task.app.data

import android.util.Log
import androidx.paging.PagingSource
import com.paging.android.task.app.api.GithubService
import com.paging.android.task.app.model.Movie
import com.paging.android.task.app.model.RepoSearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalCoroutinesApi
class MovieRepository(private val service: GithubService): PagingSource<Int, Movie>(){

    private val inMemoryCache = mutableListOf<Movie>()

    private val searchResults = ConflatedBroadcastChannel<RepoSearchResult>()

    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: lastRequestedPage
        return try {
            val response = service.loadMovies("cedb8cbe15467b907f39791c540d5d31","en-US",position)
            val repos = response.results ?: emptyList()
            var successful = false;
            if (response.results.isNotEmpty()){
                successful = true
            }
            inMemoryCache.addAll(repos)
            LoadResult.Page(
                    data = repos!!,
                    prevKey = if (position == lastRequestedPage) null else position - 1,
                    nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}
