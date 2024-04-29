package com.example.themoviedb.ui.screens.favouritemovies

import com.example.themoviedb.domain.model.MovieModel

sealed interface FavouriteMoviesState {
    data object Loading : FavouriteMoviesState
    data class Success(val movies: List<MovieModel>) : FavouriteMoviesState
    data class Error(val message: String) : FavouriteMoviesState
}

sealed class FavouriteMoviesActions {
    data object LoadFavouriteMovies : FavouriteMoviesActions()
    data class OnClickFavoriteMovie(val movie: MovieModel) : FavouriteMoviesActions()
}