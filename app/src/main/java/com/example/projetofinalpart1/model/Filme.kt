package com.example.projetofinalpart1.model

import java.util.*
import kotlin.collections.ArrayList

class Filme(
    var nomeFilme: String,
    var nomeCinema: String,
    var avaliacao: String,
    var dataVisualizacao: String,
    var observacoes: String,
    var novasFotografias: List<String>, // replace ArrayList<String> with List<String>
    var imagemCartaz: Int,
    var genero: String,
    var sinopse: String,
    var dataLancamento: String,
    var avaliacaoImdb: String,
    var linkImdb:String,
    var avaliado:Boolean,
    val uuid: String = UUID.randomUUID().toString()

){
    var fotografia: ArrayList<String> = arrayListOf<String>()

    init {
        fotografia.addAll(novasFotografias)
    }

    // add a method to replace the fotografia ArrayList
    fun substituirFotografias(novasFotografias: List<String>) {
        fotografia.clear()
        fotografia.addAll(novasFotografias)
    }
}