package com.example.cinema.model

data class Movie(
    val cinema: Cinema = getDefaultMovie(),
    val description: String = ""
)

fun getDefaultMovie() = Cinema("Name", 2020, 5.8)
