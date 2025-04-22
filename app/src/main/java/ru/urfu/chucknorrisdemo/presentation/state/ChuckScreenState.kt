package ru.urfu.chucknorrisdemo.presentation.state

interface ChuckScreenState {
    val categories: List<String>
    val selectedCategory: String
    val joke: String
    val isLoading: Boolean
}