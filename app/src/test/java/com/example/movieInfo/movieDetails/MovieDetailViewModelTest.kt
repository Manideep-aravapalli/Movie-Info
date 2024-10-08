package com.example.movieInfo.movieDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieInfo.data.models.MovieDetailsResponse
import com.example.movieInfo.data.models.Rating
import com.example.movieInfo.data.repository.MovieRepository
import com.example.movieInfo.presentation.movieDetails.MovieDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel
    private val movieRepository: MovieRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(movieRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `loadMoviesDetails successfully updates state with movies`() = runTest {
        // Arrange
        val ratings = listOf(Rating("Rotten Tomatoes", "7.2/10"))
        val expectedMovieDetails = MovieDetailsResponse("1", "2016", "Movie", "2016",
            "182 min",  "\"Action\", \"Adventure\", \"Sci-Fi\"","Zack Snyder",
            "David S. Goyer, Chris Terrio","\"Amy Adams\", \"Ben Affleck\", \"Henry Cavill\"",
            "Batman is manipulated by Lex Luthor to fear Superman. Superman´s existence is meanwhile dividing the world and he is framed for murder during an international crisis. The heroes clash and force the neutral Wonder Woman to reemerge.",
            "English","United States","2 nominations","https://m.media-amazon.com/images/M/MV5BOTRlNWQwM2ItNjkyZC00MGI3LThkYjktZmE5N2FlMzcyNTIyXkEyXkFqcGdeQXVyMTEyNzgwMDUw._V1_SX300.jpg",
            ratings,"N/A","7.2","74,123","tt18689424","movie","N/A","N/A","N/A","N/A","true")

        coEvery { movieRepository.getMovieDetails("1") } returns Response.success(expectedMovieDetails)

        // Act
        viewModel.loadMovieBasedOnId("tt18689424")

        // Assert
        val actualState = viewModel.movieDetailsState.first()
        assertEquals(expectedMovieDetails, actualState.movieDetails)
        assertEquals(false, actualState.isLoading)
        assertEquals(null, actualState.errorMessage)
    }

    @Test
    fun `loadMoviesDetails returns error message on failure`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { movieRepository.getMovies("",1) } throws Exception(errorMessage)

        // Act
        viewModel.loadMovieBasedOnId("")

        // Assert
        val actualState = viewModel.movieDetailsState.first()
        assertEquals(null, actualState.movieDetails)
        assertEquals(false, actualState.isLoading)
        assertEquals(errorMessage, actualState.errorMessage)
    }
}