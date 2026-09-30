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

class UpcomingViewModel : ViewModel() {
    private val services = TmdbApiServices()
    private val _status = MutableStateFlow(ApiStatus.NOT_STARTED)
    private val _list = MutableStateFlow(emptyList<Movie>())

    val status = _status.asStateFlow()
    val list = _list.asStateFlow()

    init {
        if (_list.value.isEmpty() ){
            getUpcomingMovies()
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            try {
                _status.update { ApiStatus.FETCHING }
                val response = services.getUpcomingMovies()
                _list.update { response.results }
                _status.update { ApiStatus.SUCCESS }
            }catch (e: Exception) {
                Log.e("FetchUpcomingMovies", e.message ?: "Error when fetching data")
                _status.update { ApiStatus.FAILED }
            }
        }
    }
}