package com.github.afrizalsebastian.compose_movieclone.services

import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.models.MovieListApiResponse
import retrofit2.http.GET

interface TmdbApi {
    @GET("trending/movie/day")
    suspend fun getPopularMovies(): MovieListApiResponse
}