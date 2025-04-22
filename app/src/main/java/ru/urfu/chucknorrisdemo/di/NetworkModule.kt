package ru.urfu.chucknorrisdemo.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.urfu.chucknorrisdemo.data.api.ChuckApi

private const val BASE_URL = "https://api.chucknorris.io/"

val networkModule = module {
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideChuckApi(get()) }
}

private fun provideOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(ChuckerInterceptor.Builder(context).build())
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideChuckApi(retrofit: Retrofit): ChuckApi {
    return ChuckApi.create(retrofit)
}