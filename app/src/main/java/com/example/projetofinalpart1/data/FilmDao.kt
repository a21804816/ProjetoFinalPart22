package com.example.projetofinalpart1.data

import androidx.constraintlayout.helper.widget.Flow
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projetofinalpart1.model.Filme

@Dao
interface FilmDao {

    @Insert
    fun insert(filme: TableRoom)

    @Query("SELECT COUNT(*) FROM WatchedFilms WHERE title = :movieTitle")
    fun countMoviesWithTitle(movieTitle: String): Int

    @Query("SELECT * FROM WatchedFilms ORDER BY userDate ASC")
    fun getAll(): List<Filme>

    @Query("SELECT * FROM WatchedFilms ORDER BY imdbRating DESC")
    fun getAllOrder(): List<Filme>

    @Query("SELECT * FROM WatchedFilms WHERE userToSee = 1 ORDER BY imdbRating DESC")
    fun getAllToSee(): List<Filme>

    @Query("SELECT * FROM WatchedFilms WHERE uuid = :uuid")
    fun getFilmByUUID(uuid: String): TableRoom?

    @Query("UPDATE WatchedFilms SET userRating = :newRating, userObservations = :newObservations WHERE uuid = :filmUUID")
    fun updateFilm(filmUUID: String, newRating: String, newObservations: String): Int

    @Query("UPDATE WatchedFilms SET userToSee = :newTooSee WHERE uuid = :filmUUID")
    fun updateFilmToSee(filmUUID: String, newTooSee: Boolean): Int
}
