package com.example.projetofinalpart1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "FilmsDashboard")
data class Table2Room(
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
    var userToSee: Boolean = false

)