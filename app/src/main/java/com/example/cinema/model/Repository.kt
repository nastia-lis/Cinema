package com.example.cinema.model

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalFantastic(): List<Movie>
    fun getMovieFromLocalComedy(): List<Movie>
}