package com.github.afrizalsebastian.compose_movieclone.models

import com.google.gson.annotations.SerializedName

data class MovieListApiResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)