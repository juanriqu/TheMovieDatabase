package com.example.themoviedb.domain.repository

import com.example.themoviedb.domain.model.ErrorModel
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.utils.Either
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun fetchPopularMovies(): Flow<Either<List<MovieModel>, ErrorModel>>

    fun getFavoriteMovies(): Flow<Either<List<MovieModel>, ErrorModel>>

    fun fetchMovie(movieId: Int): Flow<Either<MovieModel, ErrorModel>>

    fun insertFavoriteMovie(movie: MovieModel): Flow<Either<Unit, ErrorModel>>

    fun deleteFavoriteMovie(movie: MovieModel): Flow<Either<Unit, ErrorModel>>

    fun searchMovies(query: String): Flow<Either<List<MovieModel>, ErrorModel>>
}