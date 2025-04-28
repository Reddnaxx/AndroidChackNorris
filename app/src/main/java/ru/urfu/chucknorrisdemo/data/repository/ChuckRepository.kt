package ru.urfu.chucknorrisdemo.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.data.local.JokeStorage
import ru.urfu.chucknorrisdemo.domain.entity.IJoke
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.utils.NetworkUtils

class ChuckRepository(
    private val api: ChuckApi,
    private val jokeStorage: JokeStorage,
    private val context: Context
) : IChuckRepository {

    override fun getCategories(): Flow<List<String>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {

            val response = withContext(Dispatchers.IO) {
                api.getCategories().execute().body() ?: emptyList()
            }
            if (response.isNotEmpty()) {
                jokeStorage.saveCategories(response)
            }
            emit(response)

        } else {
            val cachedCategories = jokeStorage.getCategories()
            if (cachedCategories != null) {
                emit(cachedCategories)
            }
        }
    }

    override fun getJokeByCategory(category: String): Flow<IJoke?> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            val response = withContext(Dispatchers.IO) {

                val joke = api.getJokeByCategory(category = category).execute().body()
                if (joke != null) {
                    jokeStorage.saveJoke(joke)
                }
                joke

            }
            emit(response)
        } else {
            emit(getCachedJoke(category))
        }
    }

    private fun getCachedJoke(category: String): IJoke? {
        val cachedJokeText = jokeStorage.getJokeByCategory(category)
        return if (cachedJokeText != null) {
            IJoke(
                categories = listOf(category),
                createdAt = "",
                iconUrl = "",
                id = "${category}_cached",
                updatedAt = "",
                url = "",
                value = cachedJokeText
            )
        } else {
            null
        }
    }
}