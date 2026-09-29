package com.github.afrizalsebastian.compose_movieclone.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter

@Composable
fun ImageCardWithLoading(
    path: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
){
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ){
        AsyncImage(
            model = path,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
            },
            contentScale = ContentScale.Fit
        )

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}