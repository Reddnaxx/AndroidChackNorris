package ru.urfu.chucknorrisdemo.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.urfu.chucknorrisdemo.domain.entity.IJoke

class JokeStorage(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE
    )
    private val gson = Gson()

    fun saveJoke(joke: IJoke) {
        if (joke.categories.isEmpty()) {
            saveJokeByCategory("random", joke.value)
        } else {
            joke.categories.forEach { category ->
                saveJokeByCategory(category, joke.value)
            }
        }
    }

    private fun saveJokeByCategory(category: String, jokeText: String) {
        sharedPreferences.edit().putString(category, jokeText).apply()
    }

    fun getJokeByCategory(category: String): String? {
        return sharedPreferences.getString(category, null)
    }

    fun saveCategories(categories: List<String>) {
        val categoriesJson = gson.toJson(categories)
        sharedPreferences.edit().putString(KEY_CATEGORIES, categoriesJson).apply()
    }


    fun getCategories(): List<String>? {
        val categoriesJson = sharedPreferences.getString(KEY_CATEGORIES, null) ?: return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(categoriesJson, type)
    }

    companion object {
        private const val PREFERENCES_NAME = "chuck_norris_jokes"
        private const val KEY_CATEGORIES = "categories"
    }
}