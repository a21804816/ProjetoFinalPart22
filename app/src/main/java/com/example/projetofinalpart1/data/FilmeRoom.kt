package com.example.projetofinalpart1.data

import com.example.projetofinalpart1.listaTodosFilmes
import com.example.projetofinalpart1.model.ObjetoFilme
import com.example.projetofinalpart1.model.listaFilmesVistos
import java.util.*

class FilmeRoom(private val dao: MovieDao) : ObjetoFilme() {

    suspend fun addMovies(
         nomeFilme: String,
         nomeCinema: String,
         avaliacao: String,
         data: String,
         observacoes: String,
         fotos: List<String>,
         onFinished: () -> Unit
     ) {
         val film = MovieRoom(titulo = nomeFilme, cinema = nomeCinema, timestamp = data)
         dao.insert(film)
    }

    suspend fun checkIfFilmExist(nomeFilme: String): Boolean {
        val movies = dao.countMoviesWithTitle(nomeFilme)
        return movies > 0
    }

}
