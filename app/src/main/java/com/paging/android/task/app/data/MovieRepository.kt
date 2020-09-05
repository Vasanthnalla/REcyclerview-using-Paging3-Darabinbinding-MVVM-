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

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Movie>()

    // keep channel of results. The channel allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = ConflatedBroadcastChannel<RepoSearchResult>()

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

//    /**
//     * Search repositories whose names match the query, exposed as a stream of data that will emit
//     * every time we get more data from the network.
//     */
//    suspend fun getSearchResultStream(): Flow<RepoSearchResult> {
//        lastRequestedPage = 1
//        inMemoryCache.clear()
//        requestAndSaveData()
//
//        return searchResults.asFlow()
//    }
//
//    suspend fun requestMore() {
//        if (isRequestInProgress) return
//        val successful = requestAndSaveData()
//        if (successful) {
//            lastRequestedPage++
//        }
//    }
//
//    suspend fun retry() {
//        if (isRequestInProgress) return
//        requestAndSaveData()
//    }
//
//    private suspend fun requestAndSaveData(): Boolean {
//        isRequestInProgress = true
//        var successful = false
//        try {
//            val response = service.loadMovies(lastRequestedPage)
//            Log.d("GithubRepository", "response $response")
//            val repos = response.results ?: emptyList()
//            inMemoryCache.addAll(repos)
//            searchResults.offer(RepoSearchResult.Success(repos))
//            successful = true
//        } catch (exception: IOException) {
//            searchResults.offer(RepoSearchResult.Error(exception))
//        } catch (exception: HttpException) {
//            searchResults.offer(RepoSearchResult.Error(exception))
//        }
//        isRequestInProgress = false
//        return successful
//    }
//
//    companion object {
//        private const val NETWORK_PAGE_SIZE = 50
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: lastRequestedPage
        return try {
            val response = service.loadMovies("cedb8cbe15467b907f39791c540d5d31","en-US",position)
            Log.d("GithubRepository", "response $response")
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
