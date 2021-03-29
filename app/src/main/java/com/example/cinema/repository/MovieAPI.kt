package com.example.cinema.repository

import com.example.cinema.model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {
    @GET("3/movie/{id}?api_key=ba94ef13c60c86a4f3cc51f6e1cb74b5")
    fun getMovie (
        @Path("id") id: Int
    ) : Call<MovieDTO>
}