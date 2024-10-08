package com.example.movieInfo.data.sources.remote

import com.example.movieInfo.data.models.MovieDetailsResponse
import com.example.movieInfo.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apiKey: String = "376078fc",
        @Query("s") search: String,
        @Query("page") page: Int
    ): Response<MovieResponse>
    //            = "Batman"
    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String = "376078fc",
        @Query("i") imdbID: String
    ): Response<MovieDetailsResponse>

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String = "376078fc",
        @Query("s") search: String,
        @Query("page") page: Int
    ): Response<MovieResponse>
}