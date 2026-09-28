package com.github.afrizalsebastian.compose_movieclone.services

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkService {
    val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}