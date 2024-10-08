package com.example.movieInfo.presentation.movieDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.movieInfo.R
import com.example.movieInfo.data.models.MovieDetailsResponse
import com.example.movieInfo.data.models.Rating
import com.example.movieInfo.presentation.common.CommonAppBar
import com.example.movieInfo.presentation.common.DetailRow
import com.example.movieInfo.presentation.common.ErrorMessage
import com.example.movieInfo.presentation.common.Loader
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailScreen(val id: String)

@Composable
fun MovieDetailScreen(
    imdbID: String?,
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state by viewModel.movieDetailsState.collectAsState()

    LaunchedEffect(imdbID) {
        viewModel.processIntent(MovieDetailsIntent.LoadMovieDetails(imdbID!!))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CommonAppBar(
            title = "Movie Details",
            showNavigationIcon = true,
            showSearchIcon = false,
            onSearchClick = {}) {
            viewModel.processIntent(MovieDetailsIntent.OnBackPress(navController))
        }

        when {
            state.isLoading -> {
                Loader()
            }

            state.errorMessage != null -> {
                ErrorMessage("Error: ${state.errorMessage}")
            }

            state.movieDetails != null -> {
                MovieDetails(movieDetails = state.movieDetails!!)
            }

            else -> {
                Text("No movie details available")
            }
        }
    }
}

@Composable
fun MovieDetails(movieDetails: MovieDetailsResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberImagePainter(movieDetails.Poster,
                builder = {
                    error(R.drawable.image_not_available)
                    placeholder(R.drawable.image_not_available)
                }),
            contentDescription = movieDetails.Title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movieDetails.Title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Movie Details
        DetailRow("Year", movieDetails.Year)
        DetailRow("Rated", movieDetails.Rated)
        DetailRow("Released", movieDetails.Released)
        DetailRow("Runtime", movieDetails.Runtime)
        DetailRow("Genre", movieDetails.Genre)
        DetailRow("Director", movieDetails.Director)
        DetailRow("Writer", movieDetails.Writer)
        DetailRow("Actors", movieDetails.Actors)
        DetailRow("Plot", movieDetails.Plot)
        DetailRow("Language", movieDetails.Language)
        DetailRow("Country", movieDetails.Country)
        DetailRow("Awards", movieDetails.Awards)
        DetailRow("IMDb Rating", movieDetails.imdbRating)
        DetailRow("IMDb Votes", movieDetails.imdbVotes)
        DetailRow("BoxOffice", movieDetails.BoxOffice)
        DetailRow("Production", movieDetails.Production)
        DetailRow("Website", movieDetails.Website)
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    val navController = rememberNavController()
    MovieDetailScreen("tt18689424", navController = navController)
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsPreview() {
    val ratings = listOf(Rating("Rotten Tomatoes", "7.2/10"))
    val expectedMovieDetails = MovieDetailsResponse(
        "1",
        "2016",
        "Movie",
        "2016",
        "182 min",
        "\"Action\", \"Adventure\", \"Sci-Fi\"",
        "Zack Snyder",
        "David S. Goyer, Chris Terrio",
        "\"Amy Adams\", \"Ben Affleck\", \"Henry Cavill\"",
        "Batman is manipulated by Lex Luthor to fear Superman. Superman´s existence is meanwhile dividing the world and he is framed for murder during an international crisis. The heroes clash and force the neutral Wonder Woman to reemerge.",
        "English",
        "United States",
        "2 nominations",
        "https://m.media-amazon.com/images/M/MV5BOTRlNWQwM2ItNjkyZC00MGI3LThkYjktZmE5N2FlMzcyNTIyXkEyXkFqcGdeQXVyMTEyNzgwMDUw._V1_SX300.jpg",
        ratings,
        "N/A",
        "7.2",
        "74,123",
        "tt18689424",
        "movie",
        "N/A",
        "N/A",
        "N/A",
        "N/A",
        "true"
    )

    MovieDetails(movieDetails = expectedMovieDetails)
}