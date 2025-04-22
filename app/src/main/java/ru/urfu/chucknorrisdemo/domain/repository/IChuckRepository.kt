package ru.urfu.chucknorrisdemo.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.urfu.chucknorrisdemo.domain.entity.IJoke

interface IChuckRepository {

    fun getCategories(): Flow<List<String>>

    fun getJokeByCategory(category: String): Flow<IJoke?>
}