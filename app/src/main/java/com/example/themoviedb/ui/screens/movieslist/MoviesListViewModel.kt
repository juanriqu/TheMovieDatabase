package com.example.themoviedb.ui.screens.movieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.domain.usecase.movies.DeleteFavouriteMovieUseCase
import com.example.themoviedb.domain.usecase.movies.GetPopularMoviesUseCase
import com.example.themoviedb.domain.usecase.movies.InsertFavouriteMovieUseCase
import com.example.themoviedb.domain.usecase.movies.SearchMovieUseCase
import com.example.themoviedb.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val insertFavouriteMovieUseCase: InsertFavouriteMovieUseCase,
    private val deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<MoviesListState>(MoviesListState.Loading)

    val stateFlow: StateFlow<MoviesListState> = _stateFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun handleActions(moviesListActions: MoviesListActions) {
        when (moviesListActions) {
            MoviesListActions.LoadMovies -> {
                loadMovies()
            }

            is MoviesListActions.OnClickMovieBookmark -> {
                insertFavouriteMovie(moviesListActions.movie)
            }

            is MoviesListActions.OnClickFavoriteMovie -> {
                deleteFavouriteMovie(moviesListActions.movie)
            }

            is MoviesListActions.OnTextQueryChange -> {
                _searchQuery.value = moviesListActions.query
                if (moviesListActions.query.isEmpty()) {
                    getAllMovies()
                } else {
                    searchMovies(moviesListActions.query)
                }
            }
        }
    }

    private fun getAllMovies() {
        getPopularMoviesUseCase
            .invoke()
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        _stateFlow.value = MoviesListState.Success(result.data)
                    }

                    is Either.Error -> {
                        _stateFlow.value = MoviesListState.Error(result.error.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun insertFavouriteMovie(movie: MovieModel) {
        insertFavouriteMovieUseCase
            .invoke(movie)
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        loadMovies()
                    }

                    is Either.Error -> {
                        _stateFlow.value = MoviesListState.Error(result.error.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteFavouriteMovie(movie: MovieModel) {
        deleteFavouriteMovieUseCase
            .invoke(movie)
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        loadMovies()
                    }

                    is Either.Error -> {
                        _stateFlow.value = MoviesListState.Error(result.error.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchMovies(query: String) {
        searchMovieUseCase
            .invoke(query)
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        _stateFlow.value = MoviesListState.Success(result.data)
                        Log.d("MoviesListViewModel", "Movies loaded successfully")
                    }

                    is Either.Error -> {
                        _stateFlow.value = MoviesListState.Error(result.error.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadMovies() {
        if (searchQuery.value.isEmpty()) {
            getAllMovies()
        } else {
            searchMovies(searchQuery.value)
        }
    }
}