package com.example.themoviedb.di

import com.example.themoviedb.data.repository.MoviesRepositoryImpl
import com.example.themoviedb.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that provides repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    /**
     * Binds the [MoviesRepositoryImpl] to the [MoviesRepository] interface.
     */
    @Binds
    fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}