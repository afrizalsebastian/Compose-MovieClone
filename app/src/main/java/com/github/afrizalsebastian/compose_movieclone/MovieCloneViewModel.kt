package com.github.afrizalsebastian.compose_movieclone

import androidx.lifecycle.ViewModel
import com.github.afrizalsebastian.compose_movieclone.models.Movie

class MovieCloneViewModel : ViewModel() {
    var selectedMovie: Movie? = null
        private set

    fun selectMovie(movie: Movie?) {
        selectedMovie = movie
    }
}