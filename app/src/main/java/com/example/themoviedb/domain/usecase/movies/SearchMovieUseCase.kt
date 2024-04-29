package com.example.themoviedb.domain.usecase.movies

import com.example.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MoviesRepository
) {
    operator fun invoke(query: String) = movieRepository.searchMovies(query)
}