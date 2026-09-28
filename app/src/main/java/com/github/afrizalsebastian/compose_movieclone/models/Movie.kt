package com.github.afrizalsebastian.compose_movieclone.models

import com.github.afrizalsebastian.compose_movieclone.constants.Constants
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int?,
    val title: String?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
){
    companion object {
        val exampleMovie = listOf<Movie>(
            Movie(id = 1, title = "Beetle Juice", name = "Beetle Juice", overview = "Beetle Juice Movie", posterPath = Constants.heroTestURL),
            Movie(id = 2, title = "Pulp Fiction", name = "Pulp Fiction", overview = "Pulp Fiction Movie", posterPath = Constants.heroTestURL2),
            Movie(id = 3, title = "The Dark Knight", name = "The Dark Knight", overview = "The dark Knight Movie", posterPath = Constants.heroTestURL3),
        )
    }
}