    package com.github.afrizalsebastian.compose_movieclone.screens

    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.aspectRatio
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Refresh
    import androidx.compose.material3.Button
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.compose.LifecycleStartEffect
    import androidx.lifecycle.compose.collectAsStateWithLifecycle
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.github.afrizalsebastian.compose_movieclone.components.ImageCardWithLoading
    import com.github.afrizalsebastian.compose_movieclone.components.YoutubePlayer
    import com.github.afrizalsebastian.compose_movieclone.constants.buildPosterPath
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

        val scrollState = rememberScrollState()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(state = scrollState)
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
                        ApiStatus.FETCHING -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                        ApiStatus.FAILED -> Box(
                            modifier = Modifier.height(16.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column() {
                                Text("Failed to fetch data")
                                IconButton(onClick = {
                                    viewModel.fetchVideoIdYoutube(movie.title ?: movie.name ?: "")
                                }, modifier = Modifier
                                    .border(
                                    width = 0.1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )) {
                                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload")
                                }
                            }
                        }

                        ApiStatus.SUCCESS -> {
                            if (videoId.isNotBlank()) {
                                YoutubePlayer(videoId)
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    ImageCardWithLoading(
                        path = buildPosterPath(movie.posterPath),
                        contentDescription = movie.title ?: movie.name,
                        modifier = Modifier
                            .height(200.dp)
                            .aspectRatio(2f/3f)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    Text(movie.title ?: movie.name ?: "",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,

                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(movie.overview ?: "",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Left
                )
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