package com.example.movieInfo.data.sources.remote

import com.example.movieInfo.data.models.MovieDetailsResponse
import com.example.movieInfo.data.models.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovies(search: String, page: Int): Response<MovieResponse> {
        return apiService.getMovies(search = search, page = page)
    }

    suspend fun getMovieDetails(imdbID: String): Response<MovieDetailsResponse> {
        return apiService.getMovieDetails(imdbID = imdbID)
    }

    suspend fun searchMovies(search: String, page: Int): Response<MovieResponse> {
        return apiService.searchMovies(search = search, page = page)
    }
}