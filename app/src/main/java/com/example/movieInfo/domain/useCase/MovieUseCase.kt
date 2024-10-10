package com.example.movieInfo.domain.useCase

import com.example.movieInfo.data.models.MovieResponse
import com.example.movieInfo.data.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(search: String, page: Int): Response<MovieResponse> {
        return movieRepository.getMovies(search, page)
    }
}