package com.example.themoviedb.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviedb.data.common.NetworkConfig
import com.example.themoviedb.domain.model.MovieModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieDTO(
    @ColumnInfo(name = "adult") @SerializedName("adult") val adult: Boolean? = null,
    @ColumnInfo(name = "backdrop_path") @SerializedName("backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) @SerializedName("id") val id: Int,
    @ColumnInfo(name = "original_language") @SerializedName("original_language") val originalLanguage: String? = null,
    @ColumnInfo(name = "original_title") @SerializedName("original_title") val originalTitle: String? = null,
    @ColumnInfo(name = "overview") @SerializedName("overview") val overview: String,
    @ColumnInfo(name = "popularity") @SerializedName("popularity") val popularity: Double? = null,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") @SerializedName("release_date") val releaseDate: String,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "video") @SerializedName("video") val video: Boolean? = null,
    @ColumnInfo(name = "vote_average") @SerializedName("vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") @SerializedName("vote_count") val voteCount: Int? = null,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean? = false
)

fun MovieDTO.toMovieModel() = MovieModel(
    id = id,
    title = title,
    rating = voteAverage.toFloat(),
    releaseDate = releaseDate,
    posterPath = NetworkConfig.IMAGE_BASE_URL + posterPath,
    backdropPath = NetworkConfig.IMAGE_BASE_URL + backdropPath,
    isFavorite = isFavorite ?: false,
    overview = overview,
)
