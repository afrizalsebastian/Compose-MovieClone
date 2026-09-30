package com.github.afrizalsebastian.compose_movieclone.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.constants.buildPosterPath
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme
import com.github.afrizalsebastian.compose_movieclone.viewmodels.HomeViewModel

@Composable
fun HorizontalMovieCard(
    title: String,
    state: HomeViewModel.ListMovieResponse,
    onReloadFailed:  () -> Unit,
    onClickPoster: (Movie?) -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            when(state.status) {
                ApiStatus.NOT_STARTED -> Box(modifier = Modifier.height(16.dp))
                ApiStatus.FETCHING -> CircularProgressIndicator()
                ApiStatus.FAILED -> Box(
                    modifier = Modifier.height(16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column() {
                        Text("Failed to fetch data")
                        Button(onClick = onReloadFailed) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload")
                        }
                    }
                }
                ApiStatus.SUCCESS -> LazyRow(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    itemsIndexed(state.list) { i, m ->
                        if (i != 0) {
                            Spacer(
                                modifier = Modifier.width(16.dp)
                            )
                        }
                        Log.i("POSTER PATH", m.posterPath ?: "")
                        ImageCardWithLoading(
                            path = buildPosterPath(m.posterPath),
                            contentDescription = m.title ?: m.name ?: "",
                            modifier = Modifier.height(125.dp)
                                .aspectRatio(2f/3f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable{
                                    onClickPoster(m)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalMovieCardPreview() {
    ComposeMovieCloneTheme() {
        HorizontalMovieCard(
            title = "Movies Trending",
            state = HomeViewModel.ListMovieResponse(),
            onReloadFailed = {},
            onClickPoster = {}
        )
    }
}