package ru.urfu.chucknorrisdemo.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.state.ChuckScreenState

class ChuckViewModel(
    private val repository: IChuckRepository
) : ViewModel() {
    private val mutableState = MutableChuckState()
    val viewState = mutableState as ChuckScreenState

    init {
        viewModelScope.launch {
            loadCategories()
        }
    }

    private suspend fun loadCategories() {
        repository.getCategories()
            .onStart { mutableState.isLoading = true }
            .onCompletion { mutableState.isLoading = false }
            .collect {
                mutableState.categories = it
            }
    }

    private suspend fun loadRandomJokeByCategory() {
        repository.getJokeByCategory(mutableState.selectedCategory)
            .onStart { mutableState.isLoading = true }
            .onCompletion { mutableState.isLoading = false }
            .collect {
                mutableState.joke = it?.value ?: "Шутка не найдена"
            }
    }

    fun onCategoryClicked(category: String) {
        mutableState.selectedCategory = category

        viewModelScope.launch {
            loadRandomJokeByCategory()
        }
    }

    private class MutableChuckState : ChuckScreenState {
        override var categories: List<String> by mutableStateOf(emptyList())
        override var selectedCategory: String by mutableStateOf("")
        override var joke: String by mutableStateOf("")
        override var isLoading: Boolean by mutableStateOf(false)
    }
}