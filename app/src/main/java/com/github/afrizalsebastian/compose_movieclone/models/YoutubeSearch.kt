package com.github.afrizalsebastian.compose_movieclone.models

data class YoutubeSearchResponse(
    val items: List<YoutubeSearchItem> = emptyList()
)

data class YoutubeSearchItem(
    val id: YoutubeSearchItemId?
)

data class YoutubeSearchItemId(
    val videoId: String?
)
