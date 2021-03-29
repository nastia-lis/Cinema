package com.example.cinema.repository

import com.example.cinema.model.MovieDTO
import okhttp3.Callback

interface DetailsRepository {
    fun getMovieDetailsFromServer(id: Int, callback: retrofit2.Callback<MovieDTO>)
}