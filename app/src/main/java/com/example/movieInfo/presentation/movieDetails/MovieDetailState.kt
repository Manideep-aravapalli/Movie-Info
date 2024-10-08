package com.example.movieInfo.presentation.movieDetails

import com.example.movieInfo.data.models.MovieDetailsResponse

data class MovieDetailState(
    val movieDetails: MovieDetailsResponse? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)