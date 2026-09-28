package com.github.afrizalsebastian.compose_movieclone.configs

import com.github.afrizalsebastian.compose_movieclone.BuildConfig

object ApiConfigs{
    val tmbd = TmdbConfigs(
        apiKey = BuildConfig.TMDB_API_KEY,
        baseUrl = BuildConfig.TMDB_BASE_URL,
    )
}