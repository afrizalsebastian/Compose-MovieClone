package com.github.afrizalsebastian.compose_movieclone.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.afrizalsebastian.compose_movieclone.constants.buildPosterPath
import com.github.afrizalsebastian.compose_movieclone.models.Movie
import com.github.afrizalsebastian.compose_movieclone.ui.theme.ComposeMovieCloneTheme

@Composable
fun VerticalListMovie(
    movie: Movie,
    modifier: Modifier = Modifier,
    toMovieDetail: (Movie?) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable{
                toMovieDetail(movie)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        ImageCardWithLoading(
            path = buildPosterPath(movie.posterPath),
            contentDescription = movie.title ?: movie.name ?: "",
            modifier = Modifier.height(125.dp)
                .aspectRatio(2f/3f)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(
            modifier.width(20.dp)
        )

        Text(
            movie.name ?: movie.title ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerticalListMoviePreview() {
    ComposeMovieCloneTheme() {
        VerticalListMovie(
            movie = Movie.exampleMovie[2],
            toMovieDetail = {}
        )
    }
}