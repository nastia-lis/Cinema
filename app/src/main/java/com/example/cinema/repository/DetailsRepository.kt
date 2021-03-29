package com.example.cinema.repository

import okhttp3.Callback

interface DetailsRepository {
    fun getMovieDetailsFromServer(requestLink: String, callback: Callback)
}