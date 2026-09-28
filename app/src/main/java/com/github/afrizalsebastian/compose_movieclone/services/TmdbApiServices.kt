package com.github.afrizalsebastian.compose_movieclone.services

import com.github.afrizalsebastian.compose_movieclone.configs.ApiConfigs

class TmdbApiServices(
    baseUrl: String = ApiConfigs.tmbd.baseUrl,
    apiKey: String = ApiConfigs.tmbd.apiKey,
): BaseApiService(
    baseUrl,
    interceptor = { chain ->
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .header("Accept", "application/json")
            .build()
        chain.proceed(request)
    }
){
    private val api: TmdbApi = createService()

    suspend fun getPopularMovies() = api.getPopularMovies()
}