package com.example.projetofinalpart1

var listaFilmes = mutableListOf<Filme>()

object Filme {

    var nomeFilme: String = ""
    var nomeCinema: String = ""
    var avaliacao: String = ""
    var dataVisualizacao: String = ""
    var observacoes: String = ""
    var fotografia: String = ""

    fun registarFilme(
        nomeRegisto: String,
        cinemaRegisto: String,
        avaliacaoRegisto: String, dataRegisto: String,
        observacoesRegisto: String,
        fotografiaRegisto: String
    ): Filme {
        if (nomeRegisto.isBlank() || cinemaRegisto.isBlank() || avaliacaoRegisto.isBlank() || dataRegisto.isBlank()) {
            return Filme
        }

        nomeFilme = nomeRegisto
        nomeCinema = cinemaRegisto
        avaliacao = avaliacaoRegisto
        dataVisualizacao = dataRegisto
        observacoes = observacoesRegisto
        fotografia = fotografiaRegisto
        return Filme
    }


}

