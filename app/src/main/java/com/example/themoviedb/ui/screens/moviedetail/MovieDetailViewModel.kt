package com.example.themoviedb.ui.screens.moviedetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.domain.usecase.movies.DeleteFavouriteMovieUseCase
import com.example.themoviedb.domain.usecase.movies.GetMovieUseCase
import com.example.themoviedb.domain.usecase.movies.InsertFavouriteMovieUseCase
import com.example.themoviedb.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val insertFavouriteMovieUseCase: InsertFavouriteMovieUseCase,
    private val deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MovieDetailState> =
        MutableStateFlow(MovieDetailState.Loading)

    val stateFlow: StateFlow<MovieDetailState> = _stateFlow.asStateFlow()

    fun handleAction(action: MovieDetailActions) {
        when (action) {
            is MovieDetailActions.GetMovie -> {
                getMovie(action.movieId)
            }

            is MovieDetailActions.OnClickAddToFavoriteMovie -> {
                insertFavouriteMovie(action.movie)
            }

            is MovieDetailActions.OnClickRemoveFavoriteMovie -> {
                deleteFavouriteMovie(action.movie)
            }
        }
    }

    private fun getMovie(movieId: Int) {
        getMovieUseCase.invoke(movieId)
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        _stateFlow.value = MovieDetailState.Success(result.data)
                    }

                    is Either.Error -> {
                        _stateFlow.value = MovieDetailState.Error(result.error.message)
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
                        getMovie(movie.id)
                        Log.d("MoviesListViewModel", "Movie inserted successfully")
                    }

                    is Either.Error -> {
                        Log.d("MoviesListViewModel", "Error inserting movie")
                        _stateFlow.value = MovieDetailState.Error(result.error.message)
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
                        getMovie(movie.id)
                        Log.d("MoviesListViewModel", "Movie deleted successfully")
                    }

                    is Either.Error -> {
                        _stateFlow.value = MovieDetailState.Error(result.error.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}
