package com.example.cinema.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val cinema: Cinema = getDefaultCinema(),
    val description: String = "Description"
) : Parcelable

fun getDefaultCinema() = Cinema("Name", 2020, 5.8)

fun getFantasticMovie(): List<Movie> {
    return listOf(
        Movie(Cinema("Fantastic 1", 2019, 6.1)),
        Movie(Cinema("Fantastic 2", 2020, 5.6)),
        Movie(Cinema("Fantastic 3", 2019, 8.1)),
        Movie(Cinema("Fantastic 4", 2018, 3.9)),
        Movie(Cinema("Fantastic 5", 2019, 7.0)),
    )
}

fun getComedyMovie(): List<Movie> {
    return listOf(
        Movie(Cinema("Comedy 1", 2019, 6.1)),
        Movie(Cinema("Comedy 2", 2020, 5.6)),
        Movie(Cinema("Comedy 3", 2019, 8.1)),
        Movie(Cinema("Comedy 4", 2018, 3.9)),
        Movie(Cinema("Comedy 5", 2019, 7.0)),
    )
}
