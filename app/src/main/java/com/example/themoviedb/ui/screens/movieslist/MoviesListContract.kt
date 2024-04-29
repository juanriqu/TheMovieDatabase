package com.example.themoviedb.ui.screens.movieslist

import com.example.themoviedb.domain.model.MovieModel


sealed interface MoviesListState {
    data object Loading : MoviesListState
    data class Success(val movies: List<MovieModel>) : MoviesListState
    data class Error(val message: String) : MoviesListState
}

sealed class MoviesListActions {
    data object LoadMovies : MoviesListActions()
    data class OnClickMovieBookmark(val movie: MovieModel) : MoviesListActions()
    data class OnClickFavoriteMovie(val movie: MovieModel) : MoviesListActions()
    data class OnTextQueryChange(val query: String) : MoviesListActions()
}