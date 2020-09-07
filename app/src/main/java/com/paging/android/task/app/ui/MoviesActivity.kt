package com.paging.android.task.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paging.android.task.app.Injection
import com.paging.android.task.app.databinding.ActivityMoviesBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var viewModel: MoviesActivityViewModel
    private val adapter = MoviesAdapter()
    private var coroutineJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(MoviesActivityViewModel::class.java)

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

            initAdapter()
    }

    private fun initAdapter() {
        val layoutManager = binding.list.layoutManager as LinearLayoutManager
        binding.list.adapter = adapter
        coroutineJob?.cancel()
        coroutineJob = lifecycleScope.launch {
            viewModel.imageData.collect {
                it.let {
                    adapter.submitData(it)
                }
            }
        }
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                    loadState.append is LoadState.Loading)

            else {

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

    }
}