package com.example.themoviedb.ui.screens.movieslist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.ui.common.components.LoadingCircularIndicator
import com.example.themoviedb.ui.common.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesListScreen(
    onNavigateToMovieDetail: (Int) -> Unit = {},
    onNavigateToFavouriteMovies: () -> Unit = {},
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val onBookmarkClick: (MovieModel) -> Unit = { movie ->
        viewModel.handleActions(MoviesListActions.OnClickMovieBookmark(movie))
    }

    val onFavouriteMovieClick: (MovieModel) -> Unit = { movie ->
        viewModel.handleActions(MoviesListActions.OnClickFavoriteMovie(movie))
    }

    val onSearchQuery: (String) -> Unit = { query ->
        viewModel.handleActions(MoviesListActions.OnTextQueryChange(query))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.handleActions(MoviesListActions.LoadMovies)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "All Movies"
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToFavouriteMovies()
            }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favourite Movies"
                )
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchBar(
                modifier = Modifier.padding(
                    start = 16.dp, end = 16.dp, bottom = 8.dp
                ),
                query = searchQuery,
                onQueryChange = {
                    onSearchQuery(it)
                },
                onSearch = {
                    onSearchQuery(searchQuery)
                },
                onActiveChange = {
                    onSearchQuery(searchQuery)
                },
                active = false,
                placeholder = { Text("Search Movies") },
                trailingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                },
            ) {}

            val state by viewModel.stateFlow.collectAsStateWithLifecycle()

            when (val moviesListState = state) {
                is MoviesListState.Loading -> {
                    LoadingCircularIndicator()
                }

                is MoviesListState.Error -> {
                    Toast.makeText(
                        LocalContext.current, "Error: ${moviesListState.message}", Toast.LENGTH_LONG
                    ).show()
                }

                is MoviesListState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(moviesListState.movies, key = { movie -> movie.id }) { item ->
                            MovieCard(
                                movie = item,
                                onClick = { onNavigateToMovieDetail(item.id) },
                                onBookmarkClick = onBookmarkClick,
                                onDeleteFavouriteClick = onFavouriteMovieClick
                            )
                        }
                    }
                }
            }
        }
    }
}
