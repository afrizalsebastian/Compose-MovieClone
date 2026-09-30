package com.github.afrizalsebastian.compose_movieclone.services

import android.util.Log
import com.github.afrizalsebastian.compose_movieclone.configs.ApiConfigs

class YoutubeApiService(
    baseUrl: String = ApiConfigs.yt.baseUrl,
    apiKey: String = ApiConfigs.yt.apiKey
): BaseApiService(
    baseUrl,
    interceptor = { chain ->
        val originalReq = chain.request()
        val url = originalReq.url.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val newRequest = originalReq.newBuilder()
            .header("Content-Type", "application/json")
            .url(url)
            .build()

        chain.proceed(newRequest)
    }
) {

    private  val api: YoutubeApi = createService()

    suspend fun searchFirsTrailerVideoId(movieTitle: String): String {
        val response = api.searchYoutubeVideo(query = "$movieTitle Trailer")
        if (response.items.isNotEmpty() ){
            return response.items[0].id?.videoId ?: ""
        }
        return ""
    }
}