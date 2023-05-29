package com.example.projetofinalpart1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    fun insert(filme: MovieRoom)

    @Query("SELECT COUNT(*) FROM FilmesVistos WHERE titulo = :movieTitle")
    fun countMoviesWithTitle(movieTitle: String): Int

}
