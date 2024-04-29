package com.example.themoviedb.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedb.data.model.movie.MovieDTO

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies where is_favorite = 1")
    suspend fun getFavoriteMovies(): List<MovieDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDTO)

    @Delete
    suspend fun deleteMovie(movie: MovieDTO)
}