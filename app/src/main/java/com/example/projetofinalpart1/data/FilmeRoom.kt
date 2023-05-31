package com.example.projetofinalpart1.data

import com.example.projetofinalpart1.model.ObjetoFilme

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

}
