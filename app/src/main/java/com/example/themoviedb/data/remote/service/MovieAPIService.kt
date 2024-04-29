package com.example.themoviedb.data.remote.service

import com.example.themoviedb.data.model.common.ResponseDTO
import com.example.themoviedb.data.model.movie.MovieDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPIService {
    @GET("3/movie/popular")
    suspend fun fetchPopularMovies(): Response<ResponseDTO<MovieDTO>>

    @GET("3/movie/{movie_id}")
    suspend fun fetchMovie(@Path("movie_id") movieId: Int): Response<MovieDTO>

    @GET("3/search/movie")
    suspend fun searchMovies(@Query("query") query: String): Response<ResponseDTO<MovieDTO>>
}