package com.example.themoviedb.domain.usecase.movies

import com.example.themoviedb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MoviesRepository
) {
    operator fun invoke(movieId: Int) = movieRepository.fetchMovie(movieId)
}