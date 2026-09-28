package com.github.afrizalsebastian.compose_movieclone.screens

sealed class Screens(val route: String) {
    object MainScreen: Screens( route = "main")
    object MovieDetailScreen: Screens(route = "movie-detail")
}