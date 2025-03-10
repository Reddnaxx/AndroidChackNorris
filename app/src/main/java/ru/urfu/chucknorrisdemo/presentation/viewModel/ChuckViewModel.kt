package ru.urfu.chucknorrisdemo.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.urfu.chucknorrisdemo.presentation.state.ChuckScreenState

class ChuckViewModel: ViewModel() {
    private val mutableState = MutableChuckState()
    val viewState = mutableState as ChuckScreenState

    init {
        mutableState.categories = listOf("animal","career","celebrity")
    }

    fun onCategoryClicked(category: String) {
        mutableState.selectedCategory = category
        mutableState.joke = "Шутка про $category"
    }

    private class MutableChuckState: ChuckScreenState {
        override var categories: List<String> by mutableStateOf(emptyList())
        override var selectedCategory: String by mutableStateOf("")
        override var joke: String by mutableStateOf("")
    }
}