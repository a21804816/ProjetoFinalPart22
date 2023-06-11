package com.example.projetofinalpart1.model

import java.util.*

class FilmeDashboard(
    var title: String,
    var released: String,
    var runtime: String,
    var genre: String,
    var actors: String,
    var plot: String,
    var poster: String,
    var imdbRating: String,
    var imdbVotes: String,
    var imdbID: String,
    var type: String,
    var userToSee: Boolean = false,
    val uuid: String = UUID.randomUUID().toString(),

) {
}