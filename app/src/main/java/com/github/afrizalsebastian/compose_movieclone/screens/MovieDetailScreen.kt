    package com.github.afrizalsebastian.compose_movieclone.screens

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Refresh
    import androidx.compose.material3.Button
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.Icon
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.compose.LifecycleStartEffect
    import androidx.lifecycle.compose.collectAsStateWithLifecycle
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.github.afrizalsebastian.compose_movieclone.components.YoutubePlayer
    import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
    import com.github.afrizalsebastian.compose_movieclone.models.Movie
    import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme
    import com.github.afrizalsebastian.compose_movieclone.viewmodels.MovieDetailViewModel

    @Composable
    fun MovieDetailScreen(
        movie: Movie,
        viewModel: MovieDetailViewModel = viewModel()
    ) {
        val status by viewModel.status.collectAsStateWithLifecycle()
        val videoId by viewModel.videoId.collectAsStateWithLifecycle()

        LifecycleStartEffect(Unit) {
            viewModel.fetchVideoIdYoutube(movie.title ?: movie.name ?: "")

            onStopOrDispose {
                /* */
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when(status) {
                        ApiStatus.NOT_STARTED -> Box(modifier = Modifier.height(16.dp))
                        ApiStatus.FETCHING -> CircularProgressIndicator()
                        ApiStatus.FAILED -> Box(
                            modifier = Modifier.height(16.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column() {
                                Text("Failed to fetch data")
                                Button(onClick = {
                                    viewModel.fetchVideoIdYoutube(movie.title ?: movie.name ?: "")
                                }) {
                                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload")
                                }
                            }
                        }

                        ApiStatus.SUCCESS -> {
                            if (videoId.isNotEmpty()) {
                                YoutubePlayer(videoId)
                            }
                        }
                    }
                }
            }
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