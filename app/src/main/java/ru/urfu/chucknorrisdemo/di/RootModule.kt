package ru.urfu.chucknorrisdemo.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.data.local.JokeStorage
import ru.urfu.chucknorrisdemo.data.repository.ChuckRepository
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

val rootModule = module {
    single { JokeStorage(androidContext()) }

    single<IChuckRepository> { ChuckRepository(get(), get(), androidContext()) }

    viewModel { ChuckViewModel(get()) }
}