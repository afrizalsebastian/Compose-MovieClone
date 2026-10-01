package com.github.afrizalsebastian.compose_movieclone.screens.main_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.afrizalsebastian.compose_movieclone.components.VerticalListMovie
import com.github.afrizalsebastian.compose_movieclone.models.ApiStatus
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.viewmodels.UpcomingViewModel

@Composable
fun UpcomingScreen(
    modifier: Modifier = Modifier,
    viewModel: UpcomingViewModel = viewModel(),
    toMovieDetail: (Movie?) -> Unit,
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val list by viewModel.list.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
    ) {
        when (status) {
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
                        viewModel.getUpcomingMovies()
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
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(list) {
                        VerticalListMovie(it,
                            toMovieDetail = toMovieDetail,
                            modifier = Modifier
                                .border(
                                    width = 0.1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }
                }
        }
    }
}