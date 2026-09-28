package com.github.afrizalsebastian.compose_movieclone.constants

object Constants {
    const val homeString = "Home"
    const val upcomingString = "Upcoming"
    const val searchString = "Search"
    const val downloadString = "Download"
    const val playString = "Play"
    const val trendingMoviesString = "Trending Movies"
    const val trendingTVString = "Trending TV Shows"
    const val topRatedMoviesString = "Top Rated Movies"
    const val topRatedTVString = "Top Rated TV Shows"

    const val heroTestURL = "/nnl6OWkyPpuMm595hmAxNW3rZFn.jpg"
    const val heroTestURL2 = "/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg"
    const val heroTestURL3 = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg"

    const val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
}

fun buildPosterPath(path: String?): String {
    if (path != null) {
        return Constants.posterBaseUrl + path
    }
    return ""
}