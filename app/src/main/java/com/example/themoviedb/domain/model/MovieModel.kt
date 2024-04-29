package com.example.themoviedb.domain.model

import com.example.themoviedb.data.model.movie.MovieDTO

data class MovieModel(
    val id: Int,
    val title: String,
    val rating: Float,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val isFavorite: Boolean? = false,
    val overview: String,
)

fun MovieModel.toMovieDTO() = MovieDTO(
    id = id,
    title = title,
    voteAverage = rating.toDouble(),
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    isFavorite = isFavorite,
    overview = overview
)
