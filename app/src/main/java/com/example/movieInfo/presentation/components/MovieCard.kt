package com.example.movieInfo.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.movieInfo.R
import com.example.movieInfo.data.models.Movie
import com.example.movieInfo.presentation.common.DetailRow

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
fun MovieCardPreview() {
    val movie = Movie("1", "Inception", "Movie", "2010", "url")
    MovieCard(movie, onClick = {})
}