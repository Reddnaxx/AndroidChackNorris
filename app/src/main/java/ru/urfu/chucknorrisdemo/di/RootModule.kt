package ru.urfu.chucknorrisdemo.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

val rootModule = module {
    viewModel { ChuckViewModel() }
}