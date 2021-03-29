package com.example.cinema.utils

import com.example.cinema.model.Cinema
import com.example.cinema.model.Movie
import com.example.cinema.model.MovieDTO

fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
    return listOf(Movie(Cinema(movieDTO.title!!, movieDTO.release_date!!, movieDTO.vote_average!!, movieDTO.id!!), movieDTO.overview!!))
}