package com.example.projetofinalpart1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.FilmeDashboard

@Dao
interface FilmDao {

    @Insert
    fun insert(filme: TableRoom)

    @Insert
    fun insertDashboard(filme: Table2Room)

    @Query("DELETE FROM FilmsDashboard")
    fun deleteAllDashboard()

    @Query("SELECT * FROM WatchedFilms WHERE title LIKE :movieTitle")
    fun getFilmByTitle(movieTitle: String): Filme?

    @Query("SELECT COUNT(*) FROM WatchedFilms WHERE title = :movieTitle")
    fun countMoviesWithTitle(movieTitle: String): Int

    @Query("SELECT COUNT(*) FROM FilmsDashboard WHERE imdbID = :imdbId")
    fun countMoviesWithId(imdbId: String): Int

    @Query("SELECT * FROM WatchedFilms ORDER BY userDate ASC")
    fun getAll(): List<Filme>

    @Query("SELECT * FROM FilmsDashboard")
    fun getAllDashboard(): List<FilmeDashboard>

    @Query("SELECT * FROM FilmsDashboard ORDER BY imdbRating DESC LIMIT 10")
    fun getDashboardTop10(): List<FilmeDashboard>

    @Query("SELECT * FROM WatchedFilms ORDER BY imdbRating DESC")
    fun getAllOrder(): List<Filme>

    @Query("SELECT * FROM WatchedFilms WHERE userToSee = 1 ORDER BY imdbRating DESC")
    fun getAllToSee(): List<Filme>

    @Query("SELECT * FROM FilmsDashboard WHERE userToSee = 1 ORDER BY imdbRating DESC")
    fun getAllToSeeDashboard(): List<FilmeDashboard>

    @Query("SELECT * FROM WatchedFilms WHERE imdbID = :imdbId")
    fun getFilmByUUID(imdbId: String): TableRoom?

    @Query("SELECT * FROM FilmsDashboard WHERE imdbID = :imdbID")
    fun getFilmByImdbIdDashboard(imdbID: String): Table2Room?

    @Query("UPDATE WatchedFilms SET userRating = :newRating, userObservations = :newObservations WHERE uuid = :filmUUID")
    fun updateFilm(filmUUID: String, newRating: String, newObservations: String): Int

    @Query("UPDATE WatchedFilms SET userToSee = :newTooSee WHERE uuid = :filmUUID")
    fun updateFilmToSee(filmUUID: String, newTooSee: Boolean): Int

    @Query("UPDATE FilmsDashboard SET userToSee = :newTooSee WHERE imdbID = :filmUUID")
    fun updateFilmToSeeDashboard(filmUUID: String, newTooSee: Boolean): Int
}
