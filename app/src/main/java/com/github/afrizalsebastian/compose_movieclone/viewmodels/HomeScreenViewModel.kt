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


    init {
        _fetchTrendingMovies()
    }

    private fun _fetchTrendingMovies() {
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
}
