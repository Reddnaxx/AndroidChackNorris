package ru.urfu.chucknorrisdemo.presentation.viewModel

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.data.local.ArticleStorage
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.state.ChuckScreenState

class ChuckViewModel(
    private val repository: IChuckRepository,
    private val articleStorage: ArticleStorage,
    private val connectivityManager: ConnectivityManager

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
        if (isNetworkAwailable()) {
            repository.getJokeByCategory(mutableState.selectedCategory)
                .onStart { mutableState.isLoading = true }
                .onCompletion { mutableState.isLoading = false }
                .collect {
                    mutableState.joke = it?.value ?: "Шутка не найдена"
                    articleStorage.saveArticle("last_joke", mutableState.joke)
                }
        } else {
            loadJokeFromStorage()
        }
    }

    private fun loadJokeFromStorage() {
        viewModelScope.launch {
            val (_, joke) = articleStorage.loadArticle()
            mutableState.joke = joke ?: "Шутка не найдена"
        }
    }

    fun onCategoryClicked(category: String) {
        mutableState.selectedCategory = category

        viewModelScope.launch {
            loadRandomJokeByCategory()
        }
    }

    private fun isNetworkAwailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private class MutableChuckState : ChuckScreenState {
        override var categories: List<String> by mutableStateOf(emptyList())
        override var selectedCategory: String by mutableStateOf("")
        override var joke: String by mutableStateOf("")
        override var isLoading: Boolean by mutableStateOf(false)
    }
}