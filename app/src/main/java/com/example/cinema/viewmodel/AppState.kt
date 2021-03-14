package com.example.cinema.viewmodel

import com.example.cinema.model.Movie

sealed class AppState {
    data class Success(val movieFantastic: List<Movie>, val movieComedy: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
