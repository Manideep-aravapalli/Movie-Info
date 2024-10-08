package com.example.movieInfo.presentation.movieDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieInfo.data.models.MovieDetailsResponse
import com.example.movieInfo.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val mutableMovieDetailsState = MutableStateFlow(MovieDetailState())
    val movieDetailsState: StateFlow<MovieDetailState> get() = mutableMovieDetailsState

    fun processIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadMovieDetails -> {
                loadMovieBasedOnId(intent.imdbID)
            }

            is MovieDetailsIntent.OnBackPress -> {
                val navController = intent.navController
                navController.popBackStack()
            }
        }
    }

    fun loadMovieBasedOnId(imdbID: String) {
        viewModelScope.launch {
            try {
                mutableMovieDetailsState.value =
                    mutableMovieDetailsState.value.copy(isLoading = true)
                val response: Response<MovieDetailsResponse> =
                    movieRepository.getMovieDetails(imdbID)
                Log.e("TAG", "Response--> ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        mutableMovieDetailsState.value = mutableMovieDetailsState.value.copy(
                            movieDetails = movieResponse,
                            errorMessage = null,
                            isLoading = false
                        )
                    } ?: run {
                        updateErrorState("No movie details found")
                    }
                } else {
                    updateErrorState(response.message())
                }
            } catch (e: Exception) {
                e.message?.let { updateErrorState(it) }
            }
        }
    }

    private fun updateErrorState(message: String) {
        mutableMovieDetailsState.value = mutableMovieDetailsState.value.copy(
            errorMessage = message,
            isLoading = false
        )
    }
}