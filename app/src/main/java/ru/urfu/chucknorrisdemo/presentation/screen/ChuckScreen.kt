package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

import androidx.compose.material3.*
import ru.urfu.chucknorrisdemo.ui.theme.ChuckNorrisDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChuckScreen() {
    val viewModel = koinViewModel<ChuckViewModel>()
    val viewState = viewModel.viewState

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = viewState.selectedCategory.ifEmpty { "Выберите категорию" },
                onValueChange = {},
                readOnly = true,
                label = { Text("Категория") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viewState.categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            viewModel.onCategoryClicked(category)
                            expanded = false
                        }
                    )
                }
            }
        }

        when {
            viewState.isLoading -> {
                CircularProgressIndicator()
            }

            else -> {
                Text(
                    text = viewState.joke,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}



@Preview
@Composable
fun ChuckScreenPreview() {
    ChuckNorrisDemoTheme { ChuckScreen() }
}
