package com.example.themoviedb.ui.screens.favouritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.model.MovieModel
import com.example.themoviedb.domain.usecase.movies.DeleteFavouriteMovieUseCase
import com.example.themoviedb.domain.usecase.movies.GetFavouriteMoviesUseCase
import com.example.themoviedb.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<FavouriteMoviesState>(FavouriteMoviesState.Loading)

    val stateFlow: StateFlow<FavouriteMoviesState> = _stateFlow.asStateFlow()

    fun handleActions(action: FavouriteMoviesActions) {
        when (action) {
            FavouriteMoviesActions.LoadFavouriteMovies -> {
                getFavouriteMovies()
            }

            is FavouriteMoviesActions.OnClickFavoriteMovie -> {
                deleteFavouriteMovie(action.movie)
            }
        }
    }

    private fun getFavouriteMovies() {
        getFavouriteMoviesUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        _stateFlow.value = FavouriteMoviesState.Success(result.data)
                    }

                    is Either.Error -> {
                        _stateFlow.value = FavouriteMoviesState.Error(result.error.message)
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
                        getFavouriteMovies()
                    }

                    is Either.Error -> {
                        _stateFlow.value = FavouriteMoviesState.Error(result.error.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}