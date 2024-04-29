package com.example.themoviedb.ui.screens.moviedetail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.ui.common.components.LoadingCircularIndicator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBackPress: () -> Unit = {},
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.handleAction(MovieDetailActions.GetMovie(movieId))
    }

    val onClickAddToFavoriteMovie: (MovieModel) -> Unit = { movie ->
        viewModel.handleAction(MovieDetailActions.OnClickAddToFavoriteMovie(movie))
    }

    val onClickRemoveFavoriteMovie: (MovieModel) -> Unit = { movie ->
        viewModel.handleAction(MovieDetailActions.OnClickRemoveFavoriteMovie(movie))
    }

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    when (val movieDetailState = state) {
        is MovieDetailState.Loading -> {
            LoadingCircularIndicator()
        }

        is MovieDetailState.Success -> {
            Scaffold(
                topBar = {
                    IconButton(onClick = {
                        onBackPress()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            if (movieDetailState.movie.isFavorite == true) {
                                onClickRemoveFavoriteMovie(movieDetailState.movie)
                            } else {
                                onClickAddToFavoriteMovie(movieDetailState.movie)
                            }
                        },
                        icon = {
                            if (movieDetailState.movie.isFavorite == true) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            } else {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        },
                        text = {
                            if (movieDetailState.movie.isFavorite == true) {
                                Text("Remove from favorites")
                            } else {
                                Text("Add to favorites")
                            }
                        }
                    )
                },
                floatingActionButtonPosition = FabPosition.Center,
            ) {
                MovieDetailBody(
                    movie = movieDetailState.movie
                )
            }
        }

        is MovieDetailState.Error -> {
            Toast.makeText(
                LocalContext.current,
                (state as MovieDetailState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun MovieDetailBody(
    movie: MovieModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = movie.posterPath,
            contentDescription = "Movie Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(all = 10.dp),
                ) {
                    Text(
                        modifier = Modifier.
                                verticalScroll(
                                    rememberScrollState()
                                )
                        ,
                        text = movie.overview,
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "MovieDetail")
private fun MovieDetailScreenPreview() {
    MovieDetailScreen(
        movieId = 0
    )
}

