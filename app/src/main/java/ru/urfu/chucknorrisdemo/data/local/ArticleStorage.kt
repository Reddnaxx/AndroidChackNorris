package ru.urfu.chucknorrisdemo.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first


class ArticleStorage(private val dataStore: DataStore<Preferences>) {
    companion object {
        val ARTICLE_TITLE_KEY: Preferences.Key<String> = stringPreferencesKey("article_title")
        val ARTICLE_CONTENT_KEY: Preferences.Key<String> = stringPreferencesKey("article_content")
    }

    suspend fun saveArticle(title: String, content: String) {
        dataStore.edit { preferences ->
            preferences[ARTICLE_TITLE_KEY] = title
            preferences[ARTICLE_CONTENT_KEY] = content
        }
    }

    suspend fun loadArticle(): Pair<String?, String?> {
        val prefs = dataStore.data.first()
        val title = prefs[ARTICLE_TITLE_KEY]
        val content = prefs[ARTICLE_CONTENT_KEY]
        return title to content
    }
}
