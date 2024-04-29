package com.example.themoviedb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviedb.ui.screens.favouritemovies.FavouriteMoviesScreen
import com.example.themoviedb.ui.screens.moviedetail.MovieDetailScreen
import com.example.themoviedb.ui.screens.movieslist.MoviesListScreen
import com.example.themoviedb.ui.theme.TheMovieDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDBTheme {
                val navController = rememberNavController()

                val onNavigateToMovieDetail: (Int) -> Unit = { movieId ->
                    navController.navigate("moviedetail/$movieId")
                }

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        MoviesListScreen(
                            onNavigateToMovieDetail = onNavigateToMovieDetail,
                            onNavigateToFavouriteMovies = {
                                navController.navigate("favouritemovies")
                            }
                        )
                    }
                    composable("favouritemovies") {
                        FavouriteMoviesScreen(
                            onNavigateToMovieDetail = onNavigateToMovieDetail,
                            onBackPress = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("moviedetail/{movieId}") {
                        val movieId =
                            navController.currentBackStackEntry?.arguments?.getString("movieId")
                        MovieDetailScreen(
                            movieId = movieId?.toInt() ?: 0,
                            onBackPress = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
