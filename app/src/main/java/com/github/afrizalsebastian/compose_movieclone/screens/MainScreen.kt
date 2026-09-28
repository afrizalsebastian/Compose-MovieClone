package com.github.afrizalsebastian.compose_movieclone.screens

import androidx.compose.foundation.background
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
import com.github.afrizalsebastian.compose_movieclone.screens.main_screen.HomeScreen
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme

@Composable
fun MainScreen(
    toMovieDetail: () -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }
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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                tabItems.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                        },
                        icon = {
                            Icon(
                                imageVector = tabIcons[index],
                                contentDescription = tab
                            )
                        },
                        modifier = Modifier
                            .background(Color.Transparent)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when(selectedTab) {
                0 -> HomeScreen(toMovieDetail = toMovieDetail)
                1 -> Text("Upcoming Screen")
                2 -> Text("Search Screen")
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