package com.github.afrizalsebastian.compose_movieclone.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.services.TmdbApiServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val service = TmdbApiServices()
    private val _status = MutableStateFlow(ApiStatus.NOT_STARTED)
    private val _list = MutableStateFlow(emptyList<Movie>())
    private val _isMovieSearch = MutableStateFlow(true)

    val status = _status.asStateFlow()
    val list = _list.asStateFlow()
    val isMovieSearch = _isMovieSearch.asStateFlow()

    init {
        if (_isMovieSearch.value) {
            fetchTrendingMovies()
        }else {
            fetchTrendingTvShow()
        }
    }

    fun changeSearchFor(searchQuery: String) {
        _isMovieSearch.update { _isMovieSearch.value.not() }

        // Empty search query back to trending
        if (searchQuery.isBlank()) {
            if (_isMovieSearch.value) {
                fetchTrendingMovies()
            }else {
                fetchTrendingTvShow()
            }
            return
        }

        if (_isMovieSearch.value) {
            fetchSearchMovie(searchQuery)
        }else {
            fetchSearchTvShow(searchQuery)
        }
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

}