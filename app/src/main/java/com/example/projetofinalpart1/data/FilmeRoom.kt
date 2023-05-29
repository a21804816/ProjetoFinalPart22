package com.example.projetofinalpart1.data

import com.example.projetofinalpart1.listaTodosFilmes
import com.example.projetofinalpart1.model.ObjetoFilme
import com.example.projetofinalpart1.model.listaFilmesVistos
import java.util.*

class FilmeRoom(private val dao: MovieDao) : ObjetoFilme() {
     override suspend fun adicionarListaVistos(
         nomeFilme: String,
         nomeCinema: String,
         avaliacao: String,
         data: String,
         observacoes: String,
         fotos: List<String>,
         onFinished: () -> Unit
     ) {
         super.adicionarListaVistos(nomeFilme, nomeCinema, avaliacao, data, observacoes, fotos,onFinished = {})
         val film = MovieRoom(titulo = nomeFilme, cinema = nomeCinema, timestamp = data)
         dao.insert(film)
    }
}
