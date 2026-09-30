package com.github.afrizalsebastian.compose_movieclone.services

import com.github.afrizalsebastian.compose_movieclone.configs.ApiConfigs
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

class TmdbApiServices(
    baseUrl: String = ApiConfigs.tmbd.baseUrl,
    apiKey: String = ApiConfigs.tmbd.apiKey,
): BaseApiService(
    baseUrl,
    interceptor = { chain ->
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()
        chain.proceed(request)
    },
    jsonStrategy = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
){
    private val api: TmdbApi = createService()

    suspend fun getPopularMovies() = api.getPopularMovies()
    suspend fun getPopularTvShow() = api.getPopularTvShow()
    suspend fun getTopRatedMovie() = api.getTopRatedMovie()
    suspend fun getTopRatedTvShow() = api.getTopRatedTvShow()

    suspend fun getUpcomingMovies() = api.getUpcomingMovies()
}