package com.example.movieInfo.presentation.home

import androidx.navigation.NavHostController

sealed class HomeIntent {
    data object LoadMovies : HomeIntent()
    data object LoadNextPage : HomeIntent()
    data class NavigateToMovieDetail(val imdbID: String, val navController: NavHostController) :
        HomeIntent()

    data class SearchBasedOnValue(val searchQuery: String) : HomeIntent() {

    }
}