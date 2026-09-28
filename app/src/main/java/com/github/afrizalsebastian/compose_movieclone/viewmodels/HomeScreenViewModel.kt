package com.github.afrizalsebastian.compose_movieclone.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.services.TmdbApiServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    data class ListMovieResponse(
        val status: ApiStatus = ApiStatus.NOT_STARTED,
        val list: List<Movie> = listOf(),
    )
    private val service = TmdbApiServices()

    private val _trendingMovies = MutableStateFlow(ListMovieResponse())
    val trendingMovies: StateFlow<ListMovieResponse> = _trendingMovies.asStateFlow()
    private val _trendingTvShow = MutableStateFlow(ListMovieResponse())
    val trendingTvShow: StateFlow<ListMovieResponse> = _trendingTvShow.asStateFlow()
    private val _topRatedMovie = MutableStateFlow(ListMovieResponse())
    val topRatedMovie: StateFlow<ListMovieResponse> = _topRatedMovie.asStateFlow()
    private val _topRatedTvShow = MutableStateFlow(ListMovieResponse())
    val topRatedTvShow: StateFlow<ListMovieResponse> = _topRatedTvShow.asStateFlow()

    private val _heroImage: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val heroImage: StateFlow<Movie?> = _heroImage.asStateFlow()


    init {
        fetchTrendingMovies()
        fetchTrendingTvShow()
        fetchTopRateMovie()
        fetchTopRateTvShow()
    }

    fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                if (_trendingMovies.value.list.isEmpty() ){
                    _trendingMovies.update {
                        it.copy(
                            status = ApiStatus.FETCHING
                        )
                    }
                    val response = service.getPopularMovies()
                    _trendingMovies.update {
                        it.copy(
                            list = response.results,
                            status = ApiStatus.SUCCESS
                        )
                    }

                    _heroImage.value = _trendingMovies.value.list.random()
                }
            }catch (e: Exception) {
                Log.e("MovieClone", e.message ?: "Error when fetching data")
                _trendingMovies.update {
                    it.copy(
                        status = ApiStatus.FAILED
                    )
                }
            }
        }
    }

    fun fetchTrendingTvShow() {
        viewModelScope.launch {
            try {
                if (_trendingTvShow.value.list.isEmpty() ){
                    _trendingTvShow.update {
                        it.copy(
                            status = ApiStatus.FETCHING
                        )
                    }
                    val response = service.getPopularTvShow()
                    _trendingTvShow.update {
                        it.copy(
                            list = response.results,
                            status = ApiStatus.SUCCESS
                        )
                    }
                }
            }catch (e: Exception) {
                Log.e("MovieClone", e.message ?: "Error when fetching data")
                _trendingTvShow.update {
                    it.copy(
                        status = ApiStatus.FAILED
                    )
                }
            }
        }
    }

    fun fetchTopRateMovie() {
        viewModelScope.launch {
            try {
                if (_topRatedMovie.value.list.isEmpty() ){
                    _topRatedMovie.update {
                        it.copy(
                            status = ApiStatus.FETCHING
                        )
                    }
                    val response = service.getTopRatedMovie()
                    _topRatedMovie.update {
                        it.copy(
                            list = response.results,
                            status = ApiStatus.SUCCESS
                        )
                    }
                }
            }catch (e: Exception) {
                Log.e("MovieClone", e.message ?: "Error when fetching data")
                _topRatedMovie.update {
                    it.copy(
                        status = ApiStatus.FAILED
                    )
                }
            }
        }
    }

    fun fetchTopRateTvShow() {
        viewModelScope.launch {
            try {
                if (_topRatedTvShow.value.list.isEmpty() ){
                    _topRatedTvShow.update {
                        it.copy(
                            status = ApiStatus.FETCHING
                        )
                    }
                    val response = service.getTopRatedTvShow()
                    _topRatedTvShow.update {
                        it.copy(
                            list = response.results,
                            status = ApiStatus.SUCCESS
                        )
                    }
                }
            }catch (e: Exception) {
                Log.e("MovieClone", e.message ?: "Error when fetching data")
                _topRatedTvShow.update {
                    it.copy(
                        status = ApiStatus.FAILED
                    )
                }
            }
        }
    }
}
