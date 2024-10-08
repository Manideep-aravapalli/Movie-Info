package com.example.movieInfo.presentation.home

import com.example.movieInfo.data.models.Movie

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)