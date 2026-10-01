package com.github.afrizalsebastian.compose_movieclone.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.services.TmdbApiServices
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.isNotBlank
import kotlin.time.Duration.Companion.milliseconds

class SearchViewModel : ViewModel() {
    private val service = TmdbApiServices()
    private val _searchQuery = MutableStateFlow("")
    private val _status = MutableStateFlow(ApiStatus.NOT_STARTED)
    private val _list = MutableStateFlow(emptyList<Movie>())
    private val _isMovieSearch = MutableStateFlow(true)

    val searchQuery = _searchQuery.asStateFlow()
    val status = _status.asStateFlow()
    val list = _list.asStateFlow()
    val isMovieSearch = _isMovieSearch.asStateFlow()

    init {
        observeSearch()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500.milliseconds)
                .collectLatest {
                    doFetchMovies()
                }
        }
    }

    fun changeSearchFor() {
        _isMovieSearch.update { _isMovieSearch.value.not() }
        _searchQuery.update { "" }
    }

    fun onChangeSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                _status.update { ApiStatus.FETCHING }
                val response = service.getPopularMovies()
                _list.update { response.results }
                _status.update { ApiStatus.SUCCESS }
            }catch (e: Exception) {
                Log.e("FetchTrendingMovie", e.message ?: "Error when fetching data")
                _status.update { ApiStatus.FAILED }
            }
        }
    }

    fun fetchTrendingTvShow() {
        viewModelScope.launch {
            try {
                _status.update { ApiStatus.FETCHING }
                val response = service.getPopularTvShow()
                _list.update { response.results }
                _status.update { ApiStatus.SUCCESS }
            } catch (e: Exception) {
                Log.e("FetchTrendingTVShow", e.message ?: "Error when fetching data")
                _status.update { ApiStatus.FAILED }
            }
        }
    }

    fun fetchSearchMovie(searchQuery: String) {
        viewModelScope.launch {
            try {
                _status.update { ApiStatus.FETCHING }
                val response = service.searchMovies(searchQuery)
                _list.update { response.results }
                _status.update { ApiStatus.SUCCESS }
            } catch (e: Exception) {
                Log.e("FetchSearchMovie", e.message ?: "Error when fetching data")
                _status.update { ApiStatus.FAILED }
            }
        }
    }

    fun fetchSearchTvShow(searchQuery: String) {
        viewModelScope.launch {
            try {
                _status.update { ApiStatus.FETCHING }
                val response = service.searchTvShows(searchQuery)
                _list.update { response.results }
                _status.update { ApiStatus.SUCCESS }
            } catch (e: Exception) {
                Log.e("FetchSearchTvShow", e.message ?: "Error when fetching data")
                _status.update { ApiStatus.FAILED }
            }
        }
    }

    fun doFetchMovies() {
        if (_searchQuery.value.isNotBlank()) {
            if (_isMovieSearch.value) {
                fetchSearchMovie(_searchQuery.value)
            }else {
                fetchSearchTvShow(_searchQuery.value)
            }

            return
        }

        trendingPart()
    }

    fun trendingPart() {
        if (_isMovieSearch.value) {
            fetchTrendingMovies()
        }else {
            fetchTrendingTvShow()
        }
    }
}