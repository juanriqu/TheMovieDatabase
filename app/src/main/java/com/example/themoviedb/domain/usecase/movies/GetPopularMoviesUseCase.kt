package com.example.themoviedb.domain.usecase.movies

import com.example.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke() = moviesRepository.fetchPopularMovies()
}