package com.example.projetofinalpart1.model

import java.util.*

class Filme(
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
    var userAvaliated: Boolean,
    var userPhotos: String,
    var userObservations:String,
    var userCinema:String,
    var userRating:String,
    var userDate:String,
    val uuid: String = UUID.randomUUID().toString(),
    var userToSee: Boolean = false
) {
}