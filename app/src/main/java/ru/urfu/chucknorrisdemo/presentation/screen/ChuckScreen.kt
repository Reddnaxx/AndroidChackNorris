package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

@Composable
fun ChuckScreen() {
    val viewModel = koinViewModel<ChuckViewModel>()
    val viewState = viewModel.viewState

    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { expanded = true }) {
            Text(viewState.selectedCategory.ifEmpty { "Выбрать категорию" })
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewState.categories.forEach { 
                Text(
                    it,
                    Modifier
                        .clickable {
                            viewModel.onCategoryClicked(it)
                            expanded = false
                        }
                        .padding(8.dp)
                )
                Divider()
            }

        }
        Text(text = viewState.joke)
    }
}

@Preview
@Composable
fun ChuckScreenPreview() {
    ChuckScreen()
}
