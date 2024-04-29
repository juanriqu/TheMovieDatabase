package com.example.themoviedb.domain.usecase.movies

import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class DeleteFavouriteMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(movie: MovieModel) = moviesRepository.deleteFavoriteMovie(movie)
}