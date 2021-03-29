package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.*
import com.example.cinema.repository.DetailsRepository
import com.example.cinema.repository.DetailsRepositoryImpl
import com.example.cinema.utils.convertDtoToModel
import com.example.cinema.repository.RemoteDataSource
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class DetailsViewModel(private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
 ) : ViewModel() {

     fun getLiveData() = detailsLiveData

    fun getMovieFromRemoteSource(requestLink: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(requestLink, callback)
    }

    private val callback = object : Callback {
        @Throws(IOException::class)
        override fun onResponse(call: Call?, response: Response) {
            val serverResponse: String? = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable("Error Server"))
                }
            )
        }
        override fun onFailure(call: Call?, e: IOException?) {
            detailsLiveData.postValue(AppState.Error(Throwable(e?.message ?: "Error Server")))
        }
    }

    private fun checkResponse(serverResponse: String): AppState {
        val movieDTO: MovieDTO = Gson().fromJson(serverResponse, MovieDTO::class.java)
        return if (movieDTO.title.isNullOrEmpty() || movieDTO.release_date.isNullOrEmpty() || movieDTO.vote_average == null || movieDTO.overview.isNullOrEmpty()) {
            AppState.Error(Throwable("Error Server"))
        } else {
            AppState.Success(convertDtoToModel(movieDTO), convertDtoToModel(movieDTO))
        }
    }
}