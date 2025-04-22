package ru.urfu.chucknorrisdemo.data.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import ru.urfu.chucknorrisdemo.domain.entity.IJoke

interface ChuckApi {

    @GET("jokes/categories")
    fun getCategories(): Call<List<String>>

    @GET("jokes/random")
    fun getJokeByCategory(@Query("category") category: String): Call<IJoke?>

    companion object {

        fun create(retrofit: Retrofit): ChuckApi {
            return retrofit.create(ChuckApi::class.java)
        }
    }
}