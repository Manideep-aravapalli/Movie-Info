package com.example.movieInfo.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchHandler(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    AddSearchEditText(
        searchQuery = searchQuery,
        onSearchQueryChange = { newQuery ->
            if (newQuery.length >= 3 || newQuery.isEmpty()) {
                onSearchQueryChange(newQuery)
            }
        }
    )
}

@Composable
fun AddSearchEditText(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text(text = "Search Movies...") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchHandlerPreview() {
    SearchHandler(searchQuery = "", onSearchQueryChange = {})
}
