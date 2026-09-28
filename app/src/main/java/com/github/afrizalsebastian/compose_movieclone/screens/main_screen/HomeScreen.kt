package com.github.afrizalsebastian.compose_movieclone.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.afrizalsebastian.compose_movieclone.components.HorizontalMovieCard
import com.github.afrizalsebastian.compose_movieclone.components.ImageCardWithLoading
import com.github.afrizalsebastian.compose_movieclone.constants.Constants
import com.github.afrizalsebastian.compose_movieclone.constants.buildPosterPath
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme
import com.github.afrizalsebastian.compose_movieclone.viewmodels.HomeScreenViewModel


@Composable
fun HomeScreen(
    toMovieDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val trendingMovies by viewModel.trendingMovies.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .padding(10.dp)
    ) {
        item {
            ImageCardWithLoading(
                path = buildPosterPath(Constants.heroTestURL2),
                contentDescription = "Hero Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f/3f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = toMovieDetail,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(Constants.playString)
                }

                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) {
                    Text(Constants.downloadString)
                }
            }

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            HorizontalMovieCard(
                "Movie Trending",
                trendingMovies,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            HorizontalMovieCard(
                "Movie Trending",
                trendingMovies,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            HorizontalMovieCard(
                "Movie Trending",
                trendingMovies,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            HorizontalMovieCard(
                "Movie Trending",
                trendingMovies,
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ComposeMovieCloneTheme() {
        HomeScreen(toMovieDetail = {})
    }
}