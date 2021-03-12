package com.example.cinema

sealed class AppState {
    data class Success(val movieData: Any) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
