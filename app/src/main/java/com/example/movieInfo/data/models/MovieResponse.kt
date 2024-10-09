package com.example.movieInfo.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search")
    @Expose
    val search: List<Movie>,

    @SerializedName("totalResults")
    @Expose
    val totalResults: String,

    @SerializedName("Response")
    @Expose
    val response: String
)
