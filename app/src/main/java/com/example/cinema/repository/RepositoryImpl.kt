package com.example.cinema.repository

import com.example.cinema.model.Movie
import com.example.cinema.model.getComedyMovie
import com.example.cinema.model.getFantasticMovie

class RepositoryImpl: Repository {
    override fun getMovieFromServer() = Movie()

    override fun getMovieFromLocalFantastic() = getFantasticMovie()

    override fun getMovieFromLocalComedy() = getComedyMovie()
}