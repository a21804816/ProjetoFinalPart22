package com.example.projetofinalpart1.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.FilmeApi
import com.example.projetofinalpart1.model.FilmeDashboard
import com.example.projetofinalpart1.model.ObjetoFilme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class FilmeRepository(
    private val context: Context,
    private val local: FilmeRoom,
    private val remote: FilmeOkHttp
) : ObjetoFilme() {

    suspend fun getFilmList(onFinished: (Result<List<Filme>>) -> Unit) {
        local.getFilmList { result ->
            onFinished(result)
        }
    }

    suspend fun getFilmListDashboard(onFinished: (Result<List<FilmeDashboard>>) -> Unit) {
        local.getFilmListDashboard { result ->
            onFinished(result)
        }
    }

    suspend fun getDashboardTop10(onFinished: (Result<List<FilmeDashboard>>) -> Unit) {
        local.getDashboardTop10 { result ->
            onFinished(result)
        }
    }

    suspend fun getFilmToSeeList(onFinished: (Result<List<Filme>>) -> Unit) {
        local.getFilmToSeeList { result ->
            onFinished(result)
        }
    }

    suspend fun getFilmToSeeListDashboard(onFinished: (Result<List<Filme>>) -> Unit) {
        local.getFilmToSeeListDashboard { result ->
            onFinished(result)
        }
    }

    suspend fun getFilmListOrder(onFinished: (Result<List<Filme>>) -> Unit) {
        local.getFilmListOrder { result ->
            onFinished(result)
        }
    }

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
        Log.i("APP", "A adicionar Filme...")
        CoroutineScope(Dispatchers.IO).launch {
            local.addMovies(
                title,
                released,
                runtime,
                genre,
                actors,
                plot,
                poster,
                imdbRating,
                imdbVotes,
                imdbID,
                type,
                userAvaliated,
                userPhotos,
                userObservations,
                userCinema,
                userRating,
                userDate,
                userToSee,
            )
        }
    }

    suspend fun addMoviesDasboard(
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
        type: String
    ) {
        Log.i("APP", "A adicionar Filme...")
        CoroutineScope(Dispatchers.IO).launch {
            local.addMoviesDashboard(
                title,
                released,
                runtime,
                genre,
                actors,
                plot,
                poster,
                imdbRating,
                imdbVotes,
                imdbID,
                type
            )
        }
    }

    suspend fun checkIfFilmExist(
        nomeFilme: String,
        nomeCinema: String,
        avaliacao: String,
        data: String,
        observacoes: String,
        fotos: List<String>,
        onFinished: (Boolean, String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
             local.checkIfFilmExist(nomeFilme) {
                if (it){
                    Log.i("APP", "Existe na DB")
                    onFinished(false,"Filme existe na bd")
                }else if (ConnectivityUtil.isOnline(context)) {
                    Log.i("APP", "App online...")
                    remote.checkIfFilmExist(nomeFilme) { movies, error ->
                        if (movies != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                addMovies(
                                    movies.title,
                                    movies.released,
                                    movies.runtime,
                                    movies.genre,
                                    movies.actors,
                                    movies.plot,
                                    movies.poster,
                                    movies.imdbRating,
                                    movies.imdbVotes,
                                    movies.imdbID,
                                    movies.type,
                                    true,
                                    fotos.toString(),
                                    observacoes,
                                    nomeCinema,
                                    avaliacao,
                                    data,
                                    false
                                )
                            }
                            Log.i("APP", "Filme adicionado")
                            onFinished(true,"Filme adicionado")
                        } else {
                            Log.i("APP", "Filme não existe")
                            onFinished(false, "Filme não existe")
                        }
                    }
                }
             }
        }

    }

    suspend fun checkIfFilmExistDasboard(
        imdbId: String,
        onFinished: (Boolean, String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            local.checkIfFilmExistDashboard(imdbId){
                if (it){
                    onFinished(false, "Filme já esta na bd")
                }else{
                    remote.checkIfFilmExistDashboard(imdbId) { movies, error ->
                        if (movies != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                addMoviesDasboard(
                                    movies.title,
                                    movies.released,
                                    movies.runtime,
                                    movies.genre,
                                    movies.actors,
                                    movies.plot,
                                    movies.poster,
                                    movies.imdbRating,
                                    movies.imdbVotes,
                                    movies.imdbID,
                                    movies.type
                                )
                            }
                            Log.i("APP", "Filme adicionado")
                            onFinished(true, "Filme adicionado")
                        } else {
                            Log.i("APP", "Filme não existe")
                            onFinished(false, "Filme não existe")
                        }
                    }
                }
            }


        }

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: FilmeRepository? = null
        fun init(local: FilmeRoom, remote: FilmeOkHttp, context: Context) {
            synchronized(this) {
                if (instance == null) {
                    instance = FilmeRepository(context, local, remote)
                }
            }
        }

        fun getInstance(): FilmeRepository {
            if (instance == null) {
                throw IllegalStateException("singleton not initialized")
            }
            return instance as FilmeRepository
        }
    }

    override fun insertFilms(films: List<FilmeApi>, onFinished: () -> Unit) {}


}

