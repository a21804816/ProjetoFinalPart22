package com.example.projetofinalpart1.model

import java.util.*

class Filme(
    var nomeFilme: String,
    var nomeCinema: String,
    var avaliacao: String,
    var dataVisualizacao: String,
    var observacoes: String,
    var novasFotografias: List<String>,
    var imagemCartaz: Int,
    var genero: String,
    var sinopse: String,
    var dataLancamento: String,
    var avaliacaoImdb: String,
    var linkImdb: String,
    var avaliado: Boolean,
    val uuid: String = UUID.randomUUID().toString(),
    var paraVer: Boolean = false

) {
    var fotografia: ArrayList<String> = arrayListOf<String>()

    init {
        fotografia.addAll(novasFotografias)
    }

    fun substituirFotografias(novasFotografias: List<String>) {
        this.fotografia.clear()
        this.fotografia.addAll(novasFotografias)
    }
}