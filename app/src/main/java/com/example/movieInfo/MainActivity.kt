package com.example.movieInfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.movieInfo.navigation.AppNavigation
import com.example.movieInfo.ui.theme.MovieInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieInfoTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

