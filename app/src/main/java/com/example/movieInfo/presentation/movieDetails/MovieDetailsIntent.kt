package com.example.movieInfo.presentation.movieDetails

import androidx.navigation.NavController


sealed class MovieDetailsIntent {
    data class LoadMovieDetails(val imdbID: String) : MovieDetailsIntent()
    data class OnBackPress(val navController: NavController) : MovieDetailsIntent()
}