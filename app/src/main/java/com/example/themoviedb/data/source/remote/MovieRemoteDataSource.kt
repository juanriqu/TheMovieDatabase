package com.example.themoviedb.data.source.remote

import com.example.themoviedb.data.model.common.ErrorDTO
import com.example.themoviedb.data.model.movie.MovieDTO
import com.example.themoviedb.data.remote.service.MovieAPIService
import com.example.themoviedb.utils.Either
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieAPIService: MovieAPIService,
) {
    suspend fun fetchPopularMovies(): Either<List<MovieDTO>, ErrorDTO> {
        val response = movieAPIService.fetchPopularMovies()
        return if (response.isSuccessful) {
            Either.Success(response.body()?.result ?: emptyList())
        } else {
            Either.Error(ErrorDTO.fromErrorBody(response.errorBody()?.string()))
        }
    }

    suspend fun fetchMovie(id: Int): Either<MovieDTO, ErrorDTO> {
        val response = movieAPIService.fetchMovie(id)
        return if (response.isSuccessful) {
            Either.Success(response.body()!!)
        } else {
            Either.Error(ErrorDTO.fromErrorBody(response.errorBody()?.string()))
        }
    }

    suspend fun searchMovies(query: String): Either<List<MovieDTO>, ErrorDTO> {
        val response = movieAPIService.searchMovies(query)
        return if (response.isSuccessful) {
            Either.Success(response.body()?.result ?: emptyList())
        } else {
            Either.Error(ErrorDTO.fromErrorBody(response.errorBody()?.string()))
        }
    }
}