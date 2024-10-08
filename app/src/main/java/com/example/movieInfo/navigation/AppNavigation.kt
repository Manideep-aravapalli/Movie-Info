package com.example.movieInfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.movieInfo.presentation.home.HomeScreen
import com.example.movieInfo.presentation.movieDetails.MovieDetailScreen
import com.example.movieInfo.presentation.splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<SplashScreen> { SplashScreen(navController = navController) }
        composable<HomeScreen> { HomeScreen(navController = navController) }
        composable<MovieDetailScreen> { backStackEntry ->
            val imdbID = backStackEntry.toRoute<MovieDetailScreen>()
            MovieDetailScreen(imdbID = imdbID.id, navController = navController)
        }
//        composable(route = "search") { SearchScreen(navController) }
    }
}