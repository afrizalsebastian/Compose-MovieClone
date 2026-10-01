package com.github.afrizalsebastian.compose_movieclone.screens.main_screen

import android.media.ImageWriter
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.afrizalsebastian.compose_movieclone.components.ImageCardWithLoading
import com.github.afrizalsebastian.compose_movieclone.constants.buildPosterPath
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme
import com.github.afrizalsebastian.compose_movieclone.viewmodels.SearchViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SearchMovieScreen(
    toMovieDetail: (Movie?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val list by viewModel.list.collectAsStateWithLifecycle()
    val isMovieSearch by viewModel.isMovieSearch.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    viewModel.onChangeSearchQuery(it)
                },
                singleLine = true,
                label = { Text(if (isMovieSearch) "Search Movie" else "Search TV Show", fontWeight = FontWeight.SemiBold) },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            )

            IconButton(
                onClick = {
                    viewModel.changeSearchFor()
                },
                modifier = Modifier.border(
                    width = 0.1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
            ) {
                Icon(imageVector = if (isMovieSearch) Icons.Default.Tv else Icons.Default.Movie,
                    contentDescription = if (isMovieSearch) "Search TV Show" else "Search Movie")
            }
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

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
                        viewModel.doFetchMovies()
                    }, modifier = Modifier.border(
                        width = 0.1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload")
                    }
                }
            }
            ApiStatus.SUCCESS ->
                if (list.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Not Found ${if (isMovieSearch) "Movie" else "TV Show"}")
                    }
                }else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(list) {
                            ImageCardWithLoading(
                                path = buildPosterPath(it.posterPath),
                                contentDescription = it.title ?: it.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(2f/3f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable{
                                        toMovieDetail(it)
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
fun SearchMoviePreview() {
    ComposeMovieCloneTheme() {
        SearchMovieScreen(
            toMovieDetail = {}
        )
    }
}