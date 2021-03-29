package com.example.cinema.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val cinema: Cinema = getDefaultCinema(),
    val description: String = "Description"
) : Parcelable

fun getDefaultCinema() = Cinema("Name", "2020", 5.8, 429617)

fun getFantasticMovie() = listOf(
        Movie(Cinema("Fantastic 1", "2019", 6.1, 429617)),
        Movie(Cinema("Fantastic 2", "2020", 5.6, 550)),
        Movie(Cinema("Fantastic 3", "2019", 8.1, 429617)),
        Movie(Cinema("Fantastic 4", "2018", 3.9, 429617)),
        Movie(Cinema("Fantastic 5", "2019", 7.0, 429617)),
    )

fun getComedyMovie() = listOf(
        Movie(Cinema("Comedy 1", "2019", 6.1, 429617)),
        Movie(Cinema("Comedy 2", "2020", 5.0, 429617)),
        Movie(Cinema("Comedy 3", "2019", 8.1, 429617)),
        Movie(Cinema("Comedy 4", "2018", 3.9,429617)),
        Movie(Cinema("Comedy 5", "2019", 7.0, 429617)),
    )
