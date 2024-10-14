package com.example.movieInfo.home

import com.example.movieInfo.data.models.Movie
import com.example.movieInfo.data.models.MovieResponse
import com.example.movieInfo.domain.useCase.GetMoviesUseCase
import com.example.movieInfo.presentation.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val getMoviesUseCase: GetMoviesUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getMoviesUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `loadMovies successfully updates state with movies`() = runTest {
        // Arrange
        val movies = listOf(Movie("1", "Inception", "Movie", "2010", "url"))
        val movieResponse = MovieResponse(movies, "", "True")
        coEvery { getMoviesUseCase("Batman", 1) } returns retrofit2.Response.success(
            movieResponse
        )

        // Act
        viewModel.loadMovies("Batman", 1)

        // Assert
        viewModel.homeState.take(1).collect { state ->
            assertEquals(movies, state.movies)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.errorMessage)
        }
    }

    @Test
    fun `loadMovies returns error message on failure`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { getMoviesUseCase("Batman", 1) } throws Exception(errorMessage)

        // Act
        viewModel.loadMovies("Batman", 1)

        // Assert
        viewModel.homeState.take(1).collect { state ->
            assertEquals(emptyList<Movie>(), state.movies)
            assertEquals(false, state.isLoading)
            assertEquals(errorMessage, state.errorMessage)
        }
    }

    @Test
    fun `loadNextPage loads next movies correctly`() = runTest {
        // Arrange
        val moviesPage1 = listOf(Movie("1", "Inception", "Movie", "2010", "url"))
        val movieResponsePage1 = MovieResponse(moviesPage1, "", "True")
        coEvery { getMoviesUseCase("Batman", 1) } returns retrofit2.Response.success(
            movieResponsePage1
        )

        val moviesPage2 = listOf(Movie("2", "The Matrix", "Movie", "1999", "url"))
        val movieResponsePage2 = MovieResponse(moviesPage2, "", "True")
        coEvery { getMoviesUseCase("Batman", 2) } returns retrofit2.Response.success(
            movieResponsePage2
        )

        // Act
        viewModel.loadMovies("Batman", 1)
        viewModel.loadNextPage()

        // Assert
        viewModel.homeState.take(1).collect { state ->
            assertEquals(moviesPage1 + moviesPage2, state.movies)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.errorMessage)
        }
    }
}