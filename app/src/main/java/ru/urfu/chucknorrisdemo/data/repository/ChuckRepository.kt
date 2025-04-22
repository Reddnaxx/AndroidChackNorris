package ru.urfu.chucknorrisdemo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.domain.entity.IJoke
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository

class ChuckRepository(
    private val api: ChuckApi
) : IChuckRepository {

    override fun getCategories(): Flow<List<String>> = flow {
        val response = withContext(Dispatchers.IO) {
            api.getCategories().execute().body() ?: emptyList()
        }

        emit(response)
    }

    override fun getJokeByCategory(category: String): Flow<IJoke?> = flow {
        val response = withContext(Dispatchers.IO) {
            api.getJokeByCategory(category = category).execute().body()
        }

        emit(response)
    }
}