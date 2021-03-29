package com.example.cinema.repository

import com.example.cinema.model.Movie

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalFantastic(): List<Movie>
    fun getMovieFromLocalComedy(): List<Movie>
}