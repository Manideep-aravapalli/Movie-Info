package com.example.movieInfo.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieInfo.domain.useCase.GetMoviesUseCase
import com.example.movieInfo.presentation.movieDetails.MovieDetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase
) :
    ViewModel() {

    private val mutableHomeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = mutableHomeState

    private var currentPage = 1
    private var movieName = "Batman"
    private var handleLoadData = false

    init {
        processIntent(HomeIntent.LoadMovies)
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMovies -> {
                handleLoadData = false
                loadMovies(movieName, currentPage)
            }

            is HomeIntent.LoadNextPage -> {
                currentPage++
                handleLoadData = false
                loadMovies(movieName, currentPage)
            }

            is HomeIntent.NavigateToMovieDetail -> {
                val movieId = intent.imdbID
                val navController = intent.navController
                navController.navigate(MovieDetailScreen(id = movieId))
            }

            is HomeIntent.SearchBasedOnValue -> {
                handleLoadData = true
                currentPage = 1
                movieName = intent.searchQuery
                loadMovies(movieName, currentPage)
            }
        }
    }

    fun loadMovies(search: String, page: Int) {
        viewModelScope.launch {
            try {
                mutableHomeState.value = mutableHomeState.value.copy(isLoading = true)
                val response = getMoviesUseCase(search,page)
                Log.e("TAG", "Response--> ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        if (handleLoadData) {
                            mutableHomeState.value = mutableHomeState.value.copy(
                                movies = movieResponse.search,
                                isLoading = false
                            )
                        } else {
                            mutableHomeState.value = mutableHomeState.value.copy(
                                movies = mutableHomeState.value.movies + movieResponse.search,
                                isLoading = false
                            )
                        }
                    } ?: run {
                        mutableHomeState.value = mutableHomeState.value.copy(
                            errorMessage = "No movies found",
                            isLoading = false
                        )
                    }
                } else {
                    mutableHomeState.value = mutableHomeState.value.copy(
                        errorMessage = response.message(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                mutableHomeState.value = mutableHomeState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun loadNextPage() {
        currentPage++
        processIntent(HomeIntent.LoadMovies)
    }
}