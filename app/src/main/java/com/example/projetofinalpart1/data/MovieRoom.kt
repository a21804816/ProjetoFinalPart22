package com.example.projetofinalpart1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "FilmesVistos")
data class MovieRoom(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    val titulo: String,
    val cinema:String,
    val timestamp: String
)
