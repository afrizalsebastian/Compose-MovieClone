package com.github.afrizalsebastian.compose_movieclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.screens.main_screen.HomeScreen
import com.github.afrizalsebastian.compose_movieclone.screens.main_screen.SearchMovieScreen
import com.github.afrizalsebastian.compose_movieclone.screens.main_screen.UpcomingScreen
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme
import com.github.afrizalsebastian.compose_movieclone.viewmodels.MainViewModel

@Composable
fun MainScreen(
    toMovieDetail: (Movie?) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val tabItems = listOf(
        "Home",
        "Upcoming",
        "Search",
        "Download"
    )

    val tabIcons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.Default.Search,
        Icons.Default.Check
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .border(
                        width = 0.1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
            ) {
                tabItems.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            viewModel.selectTab(index)
                        },
                        icon = {
                            Icon(
                                imageVector = tabIcons[index],
                                contentDescription = tab
                            )
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when(selectedTab) {
                0 -> HomeScreen(toMovieDetail = toMovieDetail)
                1 -> UpcomingScreen(toMovieDetail = toMovieDetail)
                2 -> SearchMovieScreen()
                3 -> Text("Download Screen")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeMovieCloneTheme() {
        MainScreen(toMovieDetail = {})
    }
}