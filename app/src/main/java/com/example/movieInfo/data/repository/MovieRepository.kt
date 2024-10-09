package com.example.movieInfo.data.repository

import com.example.movieInfo.data.sources.remote.RemoteDataSource

class MovieRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getMovies(search: String, page: Int) = remoteDataSource.getMovies(search, page)
    suspend fun getMovieDetails(imdbID: String) = remoteDataSource.getMovieDetails(imdbID)
}