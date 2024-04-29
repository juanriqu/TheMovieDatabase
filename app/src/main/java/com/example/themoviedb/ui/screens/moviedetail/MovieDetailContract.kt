package com.example.themoviedb.ui.screens.moviedetail

import com.example.themoviedb.domain.model.MovieModel


sealed interface MovieDetailState {
    data object Loading : MovieDetailState
    data class Success(val movie: MovieModel) : MovieDetailState
    data class Error(val message: String) : MovieDetailState
}

sealed class MovieDetailActions {
    data class GetMovie(val movieId: Int) : MovieDetailActions()
    data class OnClickAddToFavoriteMovie(val movie: MovieModel) : MovieDetailActions()

    data class OnClickRemoveFavoriteMovie(val movie: MovieModel) : MovieDetailActions()
}