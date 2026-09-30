package com.github.afrizalsebastian.compose_movieclone.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.services.YoutubeApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val service = YoutubeApiService()

    private val _status = MutableStateFlow(ApiStatus.NOT_STARTED)
    private val _videoId = MutableStateFlow("")

    val status: StateFlow<ApiStatus> = _status.asStateFlow()
    val videoId: StateFlow<String> = _videoId.asStateFlow()

    fun fetchVideoIdYoutube(movieTitle: String) {
        viewModelScope.launch {
            try {
                _status.update { ApiStatus.FETCHING }
                val response = service.searchFirsTrailerVideoId(movieTitle)
                _videoId.update { response }
                _status.update { ApiStatus.SUCCESS }
            }catch (e: Exception) {
                Log.e("FetchVideoIdYoutube", e.message ?: "Error when fetching data")
                _status.update { ApiStatus.FAILED }
            }
        }
    }
}