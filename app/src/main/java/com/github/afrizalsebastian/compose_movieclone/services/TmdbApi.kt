package com.github.afrizalsebastian.compose_movieclone.services

import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.models.MovieListApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/day")
    suspend fun getPopularMovies(): MovieListApiResponse

    @GET("trending/tv/day")
    suspend fun getPopularTvShow(): MovieListApiResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MovieListApiResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(): MovieListApiResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieListApiResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MovieListApiResponse

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("query") query: String
    ): MovieListApiResponse
}