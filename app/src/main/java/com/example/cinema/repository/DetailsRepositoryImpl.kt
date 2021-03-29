package com.example.cinema.repository

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getMovieDetails(requestLink, callback)
    }
}