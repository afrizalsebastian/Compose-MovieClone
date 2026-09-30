package com.github.afrizalsebastian.compose_movieclone.services

import com.github.afrizalsebastian.compose_movieclone.models.YoutubeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("search")
    suspend fun searchYoutubeVideo(
        @Query("q") query: String,
    ): YoutubeSearchResponse
}