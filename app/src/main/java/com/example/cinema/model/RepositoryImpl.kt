package com.example.cinema.model

class RepositoryImpl: Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
    }
}