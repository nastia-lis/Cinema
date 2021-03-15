package com.example.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.viewmodel.AppState
import com.example.cinema.model.Repository
import com.example.cinema.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData(),
private val repository: Repository = RepositoryImpl()) : ViewModel() {

    fun getLiveData() = liveData

    fun getMovie() = getMovieFromLocalSource()

    private fun getMovieFromLocalSource() {
        liveData.value = AppState.Loading
        Thread {
            sleep(1000)
            liveData.postValue(
                AppState.Success(
                        repository.getMovieFromLocalFantastic(),
                        repository.getMovieFromLocalComedy()
                )
            )
        }.start()
    }
}