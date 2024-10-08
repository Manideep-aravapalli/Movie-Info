package com.example.movieInfo.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("Title")
    @Expose
    val title: String,

    @SerializedName("Year")
    @Expose
    val year: String,

    @SerializedName("Rated")
    @Expose
    val rated: String,

    @SerializedName("Released")
    @Expose
    val released: String,

    @SerializedName("Runtime")
    @Expose
    val runtime: String,

    @SerializedName("Genre")
    @Expose
    val genre: String,

    @SerializedName("Director")
    @Expose
    val director: String,

    @SerializedName("Writer")
    @Expose
    val writer: String,

    @SerializedName("Actors")
    @Expose
    val actors: String,

    @SerializedName("Plot")
    @Expose
    val plot: String,

    @SerializedName("Language")
    @Expose
    val language: String,

    @SerializedName("Country")
    @Expose
    val country: String,

    @SerializedName("Awards")
    @Expose
    val awards: String,

    @SerializedName("Poster")
    @Expose
    val poster: String,

    @SerializedName("Ratings")
    @Expose
    val ratings: List<Rating>,

    @SerializedName("Metascore")
    @Expose
    val metaScore: String,

    @SerializedName("imdbRating")
    @Expose
    val imdbRating: String,

    @SerializedName("imdbVotes")
    @Expose
    val imdbVotes: String,

    @SerializedName("imdbID")
    @Expose
    val imdbID: String,

    @SerializedName("Type")
    @Expose
    val type: String,

    @SerializedName("DVD")
    @Expose
    val dvd: String,

    @SerializedName("BoxOffice")
    @Expose
    val boxOffice: String,

    @SerializedName("Production")
    @Expose
    val production: String,

    @SerializedName("Website")
    @Expose
    val website: String,

    @SerializedName("Response")
    @Expose
    val response: String
)