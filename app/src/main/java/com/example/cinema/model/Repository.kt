package com.example.cinema.model

interface Repository {
    fun getMovieFromServer(): Movie
}