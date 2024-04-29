package com.example.themoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviedb.data.model.movie.MovieDTO
import com.example.themoviedb.data.source.local.MoviesDao

/**
 * Room database class that holds the local data for the TheMovieDB API.
 */
@Database(
    entities = [MovieDTO::class], version = 2, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Returns an instance of [MoviesDao] object to interact with the movies table.
     */
    abstract fun moviesDao(): MoviesDao
}