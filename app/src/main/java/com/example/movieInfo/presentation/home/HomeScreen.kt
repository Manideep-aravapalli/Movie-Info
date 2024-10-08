package com.example.movieInfo.presentation.home

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.movieInfo.R
import com.example.movieInfo.data.models.Movie
import com.example.movieInfo.presentation.common.CommonAppBar
import com.example.movieInfo.presentation.common.DetailRow
import com.example.movieInfo.presentation.common.ErrorMessage
import com.example.movieInfo.presentation.common.Loader
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.homeState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    BackHandler {
        (navController.context as? Activity)?.finishAffinity()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CommonAppBar(title = stringResource(id = R.string.app_name), showNavigationIcon = false,
            showSearchIcon = true, onBackPressed = {}, onSearchClick = {
                navController.navigate("search")
            })

        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            when {
                state.isLoading && state.movies.isEmpty() -> Loader()
                state.errorMessage != null -> ErrorMessage(state.errorMessage!!)
                else -> {
                    AddSearchEditText(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { newQuery ->
                            searchQuery = newQuery
                            if (newQuery.length >= 3) {
                                viewModel.processIntent(HomeIntent.SearchBasedOnValue(newQuery))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(state = listState) {
                        items(state.movies) { movie ->
                            MovieCard(movie) {
                                viewModel.processIntent(
                                    HomeIntent.NavigateToMovieDetail(
                                        movie.imdbID,
                                        navController
                                    )
                                )
                            }
                        }
                        if (state.isLoading) {
                            item { Loader("Loading more...") }
                        }
                    }

                    // Load more items when the last item is visible
                    LaunchedEffect(listState) {
                        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                            .collect { lastVisibleItemIndex ->
                                if (lastVisibleItemIndex == state.movies.size - 1 && !state.isLoading) {
                                    Log.e(
                                        "TAG",
                                        "HomeScreen ->  $lastVisibleItemIndex - ${state.movies.size}"
                                    )
                                    viewModel.processIntent(HomeIntent.LoadNextPage)
                                }
                            }
                    }
                }
            }
        }
    }
}


@Composable
fun AddSearchEditText(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text(text = "Search Movies...") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(
            width = 0.5.dp,
            color = Color.LightGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 10.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(5.dp)),
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
//            GlideImage("https://img.omdbapi.com/?apikey=376078fc&i=tt18689424")
            Image(
                painter = rememberImagePainter("http://img.omdbapi.com/?apikey=376078fc&i=tt18689424",
                    builder = {
                        error(R.drawable.image_not_available)
                        placeholder(R.drawable.image_not_available)
                    }),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),

                )
            Spacer(modifier = Modifier.width(10.dp))
            MovieDetails(movie)
        }
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    Column {
        Text(
            text = movie.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        DetailRow(label = "Type ", value = movie.type)
        DetailRow(label = "Year ", value = movie.year)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun AddSearchEditTextPreview() {
    AddSearchEditText(searchQuery = "", onSearchQueryChange = {})
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val movie = Movie("1", "Inception", "Movie", "2010", "url")
    MovieCard(movie, onClick = {})
}