package com.example.projetofinalpart1.model

import androidx.core.content.ContentProviderCompat.requireContext
import com.example.projetofinalpart1.data.MovieRoom
import com.example.projetofinalpart1.listaTodosFilmes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

var listaFilmesVistos = mutableListOf<Filme>()
var listaFilmesParaVer = mutableListOf<Filme>()

abstract class ObjetoFilme {

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
    var fotos: List<String> = listOf()

    var calendario = Calendar.getInstance()

    fun registarFilme(
        nomeRegisto: String,
        cinemaRegisto: String,
        avaliacaoRegisto: String,
        dataRegisto: String,
        observacoesRegisto: String,
        fotografiaRegisto: ArrayList<String>

    ): Boolean {
        if (nomeRegisto.isBlank() || cinemaRegisto.isBlank() || dataRegisto.isBlank()) {
            return false
        }
        for (filmeAdicionar in listaTodosFilmes) {
            if (filmeAdicionar.title == nomeRegisto) {
                return true
            }
        }
        return false
    }

    fun alterarAvaliacao(progress: Int) {
        avaliacaoFilme = progress.toString()
    }

    fun limparCampos() {
        nomeFilm = ""
        cinema = ""
        avaliacaoFilme = "5"
        data = ""
        observacoesFilme = ""
    }

    fun setCalendario(year: Int, monthOfYear: Int, dayOfMonth: Int) {
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

    fun getOperationById(uuid: String): Filme? {
        return listaTodosFilmes.find { it.uuid == uuid }
    }

    open suspend fun adicionarListaVistos(
        nomeFilme: String,
        nomeCinema: String,
        avaliacao: String,
        data: String,
        observacoes: String,
        fotos: List<String>,
        onFinished: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            for (filmeAdicionar in listaTodosFilmes) {
                if (filmeAdicionar.title == nomeFilme) {
                    filmeAdicionar.userRating = avaliacao
                    filmeAdicionar.userDate = data
                    filmeAdicionar.userObservations = observacoes
                    filmeAdicionar.substituirFotografias(fotos)
                    filmeAdicionar.userAvaliated = true
                    listaFilmesVistos.add(filmeAdicionar)
                    break
                }
            }
            // Notify that the operation is finished
            onFinished()
        }
    }

}