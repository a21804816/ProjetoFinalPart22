package com.example.projetofinalpart1.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.projetofinalpart1.model.ObjetoFilme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmeRepository(
    private val context: Context,
    private val local: FilmeRoom,
    private val remote: Search
) : ObjetoFilme() {

    suspend fun addMovies(
        nomeFilme: String,
        nomeCinema: String,
        avaliacao: String,
        data: String,
        observacoes: String,
        fotos: List<String>,
        onFinished: () -> Unit
    ) {
        Log.i("APP", "A adicionar Filme...")
        CoroutineScope(Dispatchers.IO).launch {
            local.addMovies(
                nomeFilme,
                nomeCinema,
                avaliacao,
                data,
                observacoes,
                fotos,
                onFinished = onFinished
            )
        }
    }

    fun checkIfFilmExist(nomeFilme: String) {
        var filmExistDb = false
        Log.i("APP", "ESTOU AQUI")
        CoroutineScope(Dispatchers.IO).launch {
            filmExistDb = local.checkIfFilmExist(nomeFilme)
        }
        if (ConnectivityUtil.isOnline(context) && !filmExistDb ) {
            Log.i("APP", "App online...")
            remote.checkIfFilmExist(nomeFilme) { movies, error -> }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: FilmeRepository? = null
        fun init(local: FilmeRoom, remote: Search, context: Context) {
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
}

