package com.example.cinema.repository

import com.example.cinema.model.MovieDTO
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val movieAPI = Retrofit.Builder()
        .baseUrl("https://api.tmdb.org/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(MovieAPI::class.java)

    fun getMovieDetails(id: Int, callback: retrofit2.Callback<MovieDTO>) {
        movieAPI.getMovie(id).enqueue(callback)
    }
}