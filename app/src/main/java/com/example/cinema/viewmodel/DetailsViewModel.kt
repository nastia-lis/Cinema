package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.*
import com.example.cinema.repository.DetailsRepository
import com.example.cinema.repository.DetailsRepositoryImpl
import com.example.cinema.utils.convertDtoToModel
import com.example.cinema.repository.RemoteDataSource

class DetailsViewModel(val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
 ) : ViewModel() {

    fun getMovieFromRemoteSource(id: Int) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(id, callback)
    }

    private val callback = object : retrofit2.Callback<MovieDTO> {

        override fun onResponse(
            call: retrofit2.Call<MovieDTO>,
            response: retrofit2.Response<MovieDTO>
        ) {
            val serverResponse: MovieDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable("Error Server"))
                }
            )
        }

        override fun onFailure(call: retrofit2.Call<MovieDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: "Error Server")))
        }
    }

    private fun checkResponse(movieDTO: MovieDTO): AppState {
        return if (movieDTO.title.isNullOrEmpty() || movieDTO.release_date.isNullOrEmpty() || movieDTO.vote_average == null || movieDTO.overview.isNullOrEmpty() || movieDTO.poster_path.isNullOrEmpty()) {
            AppState.Error(Throwable("Error Server"))
        } else {
            AppState.Success(convertDtoToModel(movieDTO), convertDtoToModel(movieDTO))
        }
    }
}