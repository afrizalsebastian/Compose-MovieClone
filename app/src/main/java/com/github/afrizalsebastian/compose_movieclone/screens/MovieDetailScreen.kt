package com.github.afrizalsebastian.compose_movieclone.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme

@Composable
fun MovieDetailScreen(
    movie: Movie,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Text(
            "Movie Detail Screen ${movie.title ?: movie.name ?: ""}",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    ComposeMovieCloneTheme() {
        MovieDetailScreen(
            movie = Movie.exampleMovie[0]
        )
    }
}