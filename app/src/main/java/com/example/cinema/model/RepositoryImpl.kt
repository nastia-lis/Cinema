package com.example.cinema.model

class RepositoryImpl: Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalFantastic(): List<Movie> {
        return getFantasticMovie()
    }

    override fun getMovieFromLocalComedy(): List<Movie> {
        return getComedyMovie()
    }
}