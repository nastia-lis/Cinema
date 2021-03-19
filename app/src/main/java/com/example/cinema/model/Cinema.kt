package com.example.cinema.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cinema(
    val movie: String,
    val released: Int,
    val rating: Double,
    val id: Int
    ) : Parcelable
