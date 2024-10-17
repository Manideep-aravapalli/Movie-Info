package com.example.movieInfo.presentation.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieInfo.data.models.Movie
import com.example.movieInfo.presentation.common.Loader

@Composable
fun MoviesList(
    movies: List<Movie>,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
    onMovieClick: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(movies) { movie ->
            MovieCard(movie) {
                onMovieClick(movie.imdbID)
            }
        }
        if (isLoading) {
            item { Loader("Loading more...") }
        }
    }

    // Load more items when the last item is visible
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == movies.size - 1 && !isLoading) {
                    Log.e("TAG", "MoviesList -> $lastVisibleItemIndex - ${movies.size}")
                    onLoadMore()
                }
            }
    }
}


@Preview(showBackground = true)
@Composable
fun AddSearchEditTextPreview() {
    val movie1 = Movie("1", "Inception", "Movie", "2010", "url")
    val movie2 = Movie("2", "The Dark Knight", "Movie", "2008", "url")
    val movie3 = Movie("3", "Interstellar", "Movie", "2014", "url")

    val movieList = listOf(movie1, movie2, movie3)
    MoviesList(movieList, isLoading = false, onLoadMore = {}, onMovieClick = {})
}