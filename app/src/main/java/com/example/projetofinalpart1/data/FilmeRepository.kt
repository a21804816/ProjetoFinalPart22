package com.example.projetofinalpart1.data

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

    override suspend fun adicionarListaVistos(
        nomeFilme: String,
        nomeCinema: String,
        avaliacao: String,
        data: String,
        observacoes: String,
        fotos: List<String>,
        onFinished: () -> Unit
    ) {
        if (ConnectivityUtil.isOnline(context)) {
            Log.i("APP", "App is online. Getting characters from the server...")
            remote.searchMovies(nomeFilme) { movies, error ->
                if (error != null) {

                } else {

                }
            }
        } else {
            Log.i("APP", "App is offline. Getting characters from the database...")
            CoroutineScope(Dispatchers.IO).launch {
                local.adicionarListaVistos(
                    nomeFilme,
                    nomeCinema,
                    avaliacao,
                    data,
                    observacoes,
                    fotos,
                    onFinished = onFinished
                )
                withContext(Dispatchers.Main) {
                    // Perform any additional tasks or UI updates here
                }
            }
        }
    }

    companion object {
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

