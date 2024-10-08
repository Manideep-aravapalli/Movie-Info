package com.example.movieInfo.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieInfo.presentation.home.HomeScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
object SplashScreen

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Movie Info", fontSize = 24.sp, color = Color.Black)

        // Navigate to HomeScreen.kt after 4 seconds
        LaunchedEffect(Unit) {
            delay(4000)
            navController.navigate(HomeScreen)
        }
    }
}