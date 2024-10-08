// Common.kt
package com.example.movieInfo.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    title: String,
    showNavigationIcon: Boolean,
    showSearchIcon: Boolean,
    onSearchClick: () -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        title = { Text(title) },
        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(onClick = onBackPressed) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (showSearchIcon) {
                IconButton(onClick = onSearchClick) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        }
    )
}

@Composable
fun Loader(message: String = "Loading...") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(message, fontSize = 16.sp)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String?) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            text = value ?: "N/A",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun ErrorMessage(message: String) {
    Text("Error: $message")
}


@Preview(showBackground = true)
@Composable
fun CommonAppBarPreView() {
    CommonAppBar(
        title = "Common AppBar",
        showNavigationIcon = false,
        showSearchIcon = false,
        onSearchClick = { }) {
    }
}

@Preview(showBackground = true)
@Composable
fun LoaderPreview() {
    Loader()
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    DetailRow(label = "Row", value = "Value")
}

@Preview(showBackground = true)
@Composable
fun ErrorMessagePreview() {
    ErrorMessage(message = "Network Issue..")
}