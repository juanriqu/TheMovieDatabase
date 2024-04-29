package com.example.themoviedb.ui.screens.favouritemovies

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.ui.common.components.LoadingCircularIndicator
import com.example.themoviedb.ui.common.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteMoviesScreen(
    onNavigateToMovieDetail: (Int) -> Unit,
    onBackPress: () -> Unit = {},
    viewModel: FavouriteMoviesViewModel = hiltViewModel()
) {
    val onFavouriteMovieClick: (MovieModel) -> Unit = { movie ->
        viewModel.handleActions(FavouriteMoviesActions.OnClickFavoriteMovie(movie))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.handleActions(FavouriteMoviesActions.LoadFavouriteMovies)
    }

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Favorites") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPress()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val moviesListState = state) {
            is FavouriteMoviesState.Loading -> {
                LoadingCircularIndicator()
            }

            is FavouriteMoviesState.Error -> {
                Toast.makeText(
                    LocalContext.current, "Error: ${moviesListState.message}", Toast.LENGTH_LONG
                ).show()
            }

            is FavouriteMoviesState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(moviesListState.movies, key = { movie -> movie.id }) { item ->
                        MovieCard(
                            movie = item,
                            onClick = { onNavigateToMovieDetail(item.id) },
                            onDeleteFavouriteClick = {
                                onFavouriteMovieClick(item)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "FavouriteMovies")
private fun FavouriteMoviesScreenPreview() {
    FavouriteMoviesScreen(
        onNavigateToMovieDetail = {}
    )
}

