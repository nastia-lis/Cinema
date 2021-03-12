package com.example.cinema.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()) : ViewModel() {

    fun getLiveData() = liveData

    fun getMovie() = getMovieFromLocalSource()

    private fun getMovieFromLocalSource() {
        Thread {
            sleep(1000)
            liveData.postValue(Any())
        }.start()
    }
}