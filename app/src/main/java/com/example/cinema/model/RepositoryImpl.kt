package com.example.cinema.model

class RepositoryImpl: Repository {
    override fun getMovieFromServer() = Movie()

    override fun getMovieFromLocalFantastic() = getFantasticMovie()

    override fun getMovieFromLocalComedy() = getComedyMovie()
}