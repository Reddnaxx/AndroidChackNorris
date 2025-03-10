package ru.urfu.chucknorrisdemo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.urfu.chucknorrisdemo.di.networkModule
import ru.urfu.chucknorrisdemo.di.rootModule

class ChuckApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChuckApp)
            modules(rootModule, networkModule)
        }
    }
}