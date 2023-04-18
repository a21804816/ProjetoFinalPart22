package com.example.projetofinalpart1

import android.graphics.Bitmap
import java.util.ArrayList


class Filme(
    var nomeFilme: String,
    var nomeCinema: String,
    var avaliacao: String,
    var dataVisualizacao: String,
    var observacoes: String,
    var fotografia: ArrayList<Bitmap>
)

var listaFilmes = mutableListOf<Filme>()

fun registarFilme(
    nomeRegisto: String,
    cinemaRegisto: String,
    avaliacaoRegisto: String,
    dataRegisto: String,
    observacoesRegisto: String,
    fotografiaRegisto: ArrayList<Bitmap>

): Boolean {
    if (nomeRegisto.isBlank() || cinemaRegisto.isBlank() || dataRegisto.isBlank()) {
        return false
    }
    val filme = Filme(
        nomeRegisto,
        cinemaRegisto,
        avaliacaoRegisto,
        dataRegisto,
        observacoesRegisto,
        fotografiaRegisto
    )
    listaFilmes.add(filme)
    return true
}

    fun verificarNomeFilme(nome:String): Boolean {
        if(nome.isBlank()){
            return false;
        }
        return true
    }

    fun verificarNomeCinema(nome:String): Boolean {
        if(nome.isBlank()){
            return false;
        }
        return true
    }

    fun verificarData(data:String): Boolean {
        if(data.isBlank()){
            return false;
        }
        return true
    }


