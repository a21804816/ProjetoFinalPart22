package com.example.projetofinalpart1.data

import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.FilmeApi
import com.example.projetofinalpart1.model.ObjetoFilme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmeRoom(private val dao: FilmDao) : ObjetoFilme() {

    suspend fun addMovies(
        title: String,
        released: String,
        runtime: String,
        genre: String,
        actors: String,
        plot: String,
        poster: String,
        imdbRating: String,
        imdbVotes: String,
        imdbID: String,
        type: String,
        userAvaliated: Boolean,
        userPhotos: String,
        userObservations: String,
        userCinema: String,
        userRating: String,
        userDate: String,
        userToSee: Boolean,
    ) {
        val film = TableRoom(
            title = title, released = released, runtime = runtime,
            genre = genre, actors = actors, plot = plot, poster = poster, imdbRating = imdbRating,
            imdbVotes = imdbVotes, imdbID = imdbID, type = type, userAvaliated = userAvaliated,
            userPhotos = userPhotos, userObservations = userObservations, userCinema = userCinema,
            userRating = userRating, userDate = userDate, userToSee = userToSee
        )
        dao.insert(film)
    }

    suspend fun checkIfFilmExist(nomeFilme: String, onFinished: (Boolean) -> Unit) {
        val movies = dao.countMoviesWithTitle(nomeFilme)
        onFinished(movies > 0)
    }

    fun getFilmList(onFinished: (Result<List<Filme>>) -> Unit) {
        val allFilms = dao.getAll()
        val films = allFilms.map {
            Filme(it.title, it.released, it.runtime, it.genre, it.actors, it.plot,it.poster,it.imdbRating,it.imdbVotes,it.imdbID,it.type,it.userAvaliated,it.userPhotos,it.userObservations,it.userCinema,it.userRating,it.userDate, it.uuid,it.userToSee)
        }
        onFinished(Result.success(films))
    }

    fun getFilmListOrder(onFinished: (Result<List<Filme>>) -> Unit) {
        val allFilms = dao.getAllOrder()
        val films = allFilms.map {
            Filme(it.title, it.released, it.runtime, it.genre, it.actors, it.plot,it.poster,it.imdbRating,it.imdbVotes,it.imdbID,it.type,it.userAvaliated,it.userPhotos,it.userObservations,it.userCinema,it.userRating,it.userDate, it.uuid,it.userToSee)
        }
        onFinished(Result.success(films))
    }

    fun getFilmToSeeList(onFinished: (Result<List<Filme>>) -> Unit) {
        val allFilms = dao.getAllToSee()
        val films = allFilms.map {
            Filme(it.title, it.released, it.runtime, it.genre, it.actors, it.plot,it.poster,it.imdbRating,it.imdbVotes,it.imdbID,it.type,it.userAvaliated,it.userPhotos,it.userObservations,it.userCinema,it.userRating,it.userDate, it.uuid,it.userToSee)
        }
        onFinished(Result.success(films))
    }

    override fun insertFilms(films: List<FilmeApi>, onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    suspend fun getFilmByUUID(uuid: String, onFinished: (Filme?) -> Unit) {
        withContext(Dispatchers.IO) {
            val film = dao.getFilmByUUID(uuid)
            val mappedFilm = film?.let {
                Filme(
                    it.title, it.released, it.runtime, it.genre, it.actors, it.plot, it.poster,
                    it.imdbRating, it.imdbVotes, it.imdbID, it.type, it.userAvaliated, it.userPhotos,
                    it.userObservations, it.userCinema, it.userRating, it.userDate, it.uuid, it.userToSee
                )
            }
            onFinished(mappedFilm)
        }
    }

    fun updateFilm(uuid: String, newRating: String, newObservations:String) {
        dao.updateFilm(uuid, newRating, newObservations)
    }

    fun updateFilmToSee(uuid: String, toSee: Boolean) {
        dao.updateFilmToSee(uuid, toSee)
    }
}
