package com.example.projetofinalpart1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmDao {

    @Insert
    fun insert(filme: TableRoom)

    @Query("SELECT COUNT(*) FROM WatchedFilms WHERE title = :movieTitle")
    fun countMoviesWithTitle(movieTitle: String): Int

}
