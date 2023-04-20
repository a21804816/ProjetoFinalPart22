package com.example.projetofinalpart1.model

import android.graphics.Bitmap
import java.text.SimpleDateFormat
import java.util.*

var listaFilmes = mutableListOf<Filme>()

object ObjetoFilme {

    var nomeFilm: String = ""
        private set
    var cinema: String = ""
        private set
    var avaliacaoFilme: String = ""
        private set
    var data: String = ""
        private set
    var observacoesFilme: String = ""
        private set
    var fotos: String = ""

    val calendario = Calendar.getInstance()

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

    fun alterarAvaliacao(progress: Int) {
        avaliacaoFilme = progress.toString()
    }

    fun limparCampos() {
        nomeFilm= ""
        cinema= ""
        avaliacaoFilme= ""
        data= ""
        observacoesFilme= ""
        fotos=""
    }

    fun setCalendario(view: Any, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendario.set(Calendar.YEAR, year)
        calendario.set(Calendar.MONTH, monthOfYear)
        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateData(calendario)
    }

    private fun updateData(calendario: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sd = SimpleDateFormat(myFormat, Locale.UK)
        data = sd.format(calendario.time)
    }

}