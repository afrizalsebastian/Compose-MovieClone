package com.github.afrizalsebastian.compose_movieclone

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.afrizalsebastian.compose_movieclone.models.Movie
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
        composable(Screens.MainScreen.route){ backStackEntry ->
            val sharedVeModel: MovieCloneViewModel = viewModel(
                viewModelStoreOwner = rootNavController.previousBackStackEntry ?: backStackEntry
            )

            MainScreen(
                toMovieDetail = {
                    sharedVeModel.selectMovie(it)
                    rootNavController.navigate(Screens.MovieDetailScreen.route)
                }
            )
        }

        composable(Screens.MovieDetailScreen.route) { backStackEntry ->
            val sharedVeModel: MovieCloneViewModel = viewModel(
                viewModelStoreOwner = rootNavController.previousBackStackEntry ?: backStackEntry
            )

            val movie = sharedVeModel.selectedMovie ?: Movie(0, "", "", "", "")
            MovieDetailScreen(
                movie = movie,
            )
        }
    }
}