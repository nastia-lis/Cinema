package com.example.cinema.repository

import com.example.cinema.model.MovieDTO
import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(id: Int, callback: retrofit2.Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(id, callback)
    }
}