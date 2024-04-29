package com.example.themoviedb.domain.usecase.movies

import com.example.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetFavouriteMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke() = moviesRepository.getFavoriteMovies()
}