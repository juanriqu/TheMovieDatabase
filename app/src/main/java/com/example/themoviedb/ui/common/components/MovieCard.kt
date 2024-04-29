package com.example.themoviedb.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.themoviedb.domain.model.MovieModel

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: MovieModel,
    onClick: () -> Unit = {},
    onBookmarkClick: (MovieModel) -> Unit = {},
    onDeleteFavouriteClick: (MovieModel) -> Unit = {}
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(
            start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
        )
        .shadow(elevation = 20.dp, ambientColor = Color.Black)
        .clickable {
            onClick()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxHeight()) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.5f))
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .size(25.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (movie.isFavorite == false) {
                                onBookmarkClick(movie)
                            } else {
                                onDeleteFavouriteClick(movie)
                            }
                        }, modifier = Modifier
                    ) {
                        if (movie.isFavorite == true) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Bookmarked",
                                tint = Color.Yellow
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Not Bookmarked",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(all = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movie.title,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                )

                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(
                        modifier = Modifier.size(5.dp)
                    )
                    Text(
                        text = String.format("%.1f", movie.rating),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                    Spacer(
                        modifier = Modifier.size(5.dp)
                    )
                    Text(
                        text = "- " + movie.releaseDate.split("-").first(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Preview(name = "MovieCard")
@Composable
private fun PreviewMovieCard() {
    MovieCard(movie = MovieModel(
        id = 1,
        title = "Movie Title",
        rating = 4.5f,
        releaseDate = "2022-01-01",
        posterPath = "https://image.tmdb.org/t/p/w500/8Y43POKjjKDGI9MH89NW0NAzzp8.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w500/8Y43POKjjKDGI9MH89NW0NAzzp8.jpg",
        overview = "Movie Overview Description"
    ), onBookmarkClick = {}, onDeleteFavouriteClick = {})
}