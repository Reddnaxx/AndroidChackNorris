package ru.urfu.chucknorrisdemo.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.data.repository.ChuckRepository
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

val rootModule = module {
    single<IChuckRepository> { ChuckRepository(get()) }

    viewModel { ChuckViewModel(get()) }
}