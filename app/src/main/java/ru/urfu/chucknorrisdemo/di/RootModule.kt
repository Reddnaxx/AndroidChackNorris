package ru.urfu.chucknorrisdemo.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.data.local.ArticleStorage
import ru.urfu.chucknorrisdemo.data.repository.ChuckRepository
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

val rootModule = module {
    single<IChuckRepository> { ChuckRepository(get()) }
    single<DataStore<Preferences>> {
        getDataStore(androidContext())
    }
    single<ArticleStorage> { ArticleStorage(get()) }
    single<ConnectivityManager> {androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager}
    viewModel { ChuckViewModel(get(), get(), get()) }
}

fun getDataStore(androidContext: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create {
        androidContext.preferencesDataStoreFile("default")
    }