package com.github.afrizalsebastian.compose_movieclone

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.afrizalsebastian.compose_movieclone.screens.MainScreen
import com.github.afrizalsebastian.compose_movieclone.screens.MovieDetailScreen
import com.github.afrizalsebastian.compose_movieclone.screens.Screens

@Composable
fun MovieCloneApp() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = Screens.MainScreen.route
    ){
        composable(Screens.MainScreen.route){
            MainScreen(
                toMovieDetail = {
                    rootNavController.navigate(Screens.MovieDetailScreen.route)
                }
            )
        }

        composable(Screens.MovieDetailScreen.route) {
            MovieDetailScreen()
        }
    }
}