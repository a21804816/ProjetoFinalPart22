package com.example.projetofinalpart1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "WatchedFilms")
data class TableRoom(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
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
    var userToSee: Boolean = false
)