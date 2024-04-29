package com.example.themoviedb.data.repository

import com.example.themoviedb.data.model.common.toErrorModel
import com.example.themoviedb.data.model.movie.toMovieModel
import com.example.themoviedb.data.source.local.MoviesDao
import com.example.themoviedb.data.source.remote.MovieRemoteDataSource
import com.example.themoviedb.domain.model.ErrorModel
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.domain.model.toMovieDTO
import com.example.themoviedb.domain.repository.MoviesRepository
import com.example.themoviedb.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override fun fetchPopularMovies(): Flow<Either<List<MovieModel>, ErrorModel>> {
        return flow {
            when (val result = movieRemoteDataSource.fetchPopularMovies()) {
                is Either.Success -> {
                    val favoriteMovies = moviesDao.getFavoriteMovies()
                    val movies = result.data.map { movieDTO ->
                        val favorite = favoriteMovies.find { it.id == movieDTO.id } != null
                        movieDTO.toMovieModel().copy(isFavorite = favorite)
                    }
                    emit(Either.Success(movies))
                }

                is Either.Error -> {
                    emit(
                        Either.Error(result.error.toErrorModel())
                    )
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteMovies(): Flow<Either<List<MovieModel>, ErrorModel>> {
        return flow {
            val favoriteMovies = moviesDao.getFavoriteMovies()
            if (favoriteMovies.isEmpty()) {
                emit(Either.Success(emptyList()))
            } else {
                emit(Either.Success(favoriteMovies.map { it.toMovieModel() }))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchMovie(movieId: Int): Flow<Either<MovieModel, ErrorModel>> {
        return flow {
            when (val result = movieRemoteDataSource.fetchMovie(movieId)) {
                is Either.Success -> {
                    val favoriteMovies = moviesDao.getFavoriteMovies()
                    val movie = result.data.toMovieModel()
                    val favorite = favoriteMovies.find { it.id == movie.id } != null
                    emit(Either.Success(movie.copy(isFavorite = favorite)))
                }

                is Either.Error -> {
                    emit(
                        Either.Error(result.error.toErrorModel())
                    )
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun insertFavoriteMovie(movie: MovieModel): Flow<Either<Unit, ErrorModel>> {
        return flow {
            moviesDao.insertMovie(movie.copy(isFavorite = true).toMovieDTO())
            emit(Either.Success(Unit))
        }.flowOn(Dispatchers.IO)
    }

    override fun deleteFavoriteMovie(movie: MovieModel): Flow<Either<Unit, ErrorModel>> {
        return flow {
            moviesDao.deleteMovie(movie.toMovieDTO())
            emit(Either.Success(Unit))
        }.flowOn(Dispatchers.IO)
    }

    override fun searchMovies(query: String): Flow<Either<List<MovieModel>, ErrorModel>> {
        return flow {
            when (val result = movieRemoteDataSource.searchMovies(query)) {
                is Either.Success -> {
                    val favoriteMovies = moviesDao.getFavoriteMovies()
                    val movies = result.data.map { movieDTO ->
                        val favorite = favoriteMovies.find { it.id == movieDTO.id } != null
                        movieDTO.toMovieModel().copy(isFavorite = favorite)
                    }
                    emit(Either.Success(movies))
                }

                is Either.Error -> {
                    emit(
                        Either.Error(result.error.toErrorModel())
                    )
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}