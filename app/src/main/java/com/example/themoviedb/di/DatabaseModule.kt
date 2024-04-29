package com.example.themoviedb.di

import android.content.Context
import androidx.room.Room
import com.example.themoviedb.data.local.AppDatabase
import com.example.themoviedb.data.source.local.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * This class is used to provide instances of the necessary classes that are used to access the app's local database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides the location of the asset database.
     * @return a [String] representing the path to the asset database
     */
    @Provides
    @Named("assetDB")
    fun getAssetDB(): String = "database/movies.db"

    /**
     * Provides an instance of the application's database.
     * @param context the application context
     * @return an instance of the [AppDatabase]
     */
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "movies_db")
        .fallbackToDestructiveMigration().build()

    /**
     * Provides an instance of the [MoviesDao] class.
     * @param database the application database
     * @return an instance of the [MoviesDao]
     */
    @Provides
    @Singleton
    fun provideMoviesDao(database: AppDatabase) = database.moviesDao()
}