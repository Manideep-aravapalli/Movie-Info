package com.example.movieInfo.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieInfo.R
import com.example.movieInfo.presentation.common.CommonAppBar
import com.example.movieInfo.presentation.common.ErrorMessage
import com.example.movieInfo.presentation.common.Loader
import com.example.movieInfo.presentation.components.MoviesList
import com.example.movieInfo.presentation.components.SearchHandler
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.homeState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    BackHandler {
        (navController.context as? Activity)?.finishAffinity()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CommonAppBar(title = stringResource(id = R.string.app_name), showNavigationIcon = false,
            showSearchIcon = false, onBackPressed = {}, onSearchClick = {})

        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            when {
                state.isLoading && state.movies.isEmpty() -> Loader()
                state.errorMessage != null -> ErrorMessage(state.errorMessage!!)
                else -> {
                    SearchHandler(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { newQuery ->
                            searchQuery = newQuery
                            viewModel.processIntent(HomeIntent.SearchBasedOnValue(newQuery))
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    MoviesList(
                        movies = state.movies,
                        isLoading = state.isLoading,
                        onLoadMore = { viewModel.processIntent(HomeIntent.LoadNextPage) },
                        onMovieClick = { movieId ->
                            viewModel.processIntent(
                                HomeIntent.NavigateToMovieDetail(
                                    movieId,
                                    navController
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}