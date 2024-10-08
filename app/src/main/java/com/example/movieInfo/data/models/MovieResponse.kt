package com.example.movieInfo.data.models

data class MovieResponse(
    val Search: List<Movie>,
    val totalResults: String,
    val Response: String
)