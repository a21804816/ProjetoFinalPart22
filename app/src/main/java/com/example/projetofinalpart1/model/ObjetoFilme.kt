package com.example.projetofinalpart1.model

import android.content.Context
import com.example.projetofinalpart1.listaTodosFilmes
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

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

    fun verificarCampos(
        nomeRegisto: String,
        cinemaRegisto: String,
        dataRegisto: String
    ): Boolean {
        if (nomeRegisto.isBlank() || cinemaRegisto.isBlank() || dataRegisto.isBlank()) {
            return true
        }
        return false
    }

    fun verificarNomeFilmeVazio(nome: String): Boolean {
        return nome.isBlank()
    }

    fun verificarNomeCinemaVazio(nome: String): Boolean {
        return nome.isBlank()
    }

    fun verificarDataVazio(data: String): Boolean {
        return data.isBlank()
    }

    fun alterarAvaliacao(progress: Int) {
        avaliacaoFilme = progress.toString()
    }

    private fun readCinemasFromJson(context: Context?): List<String> {
        val cinemas = mutableListOf<String>()

        try {
            val inputStream = context?.assets?.open("cinemas.json")
            val size = inputStream?.available()
            val buffer = ByteArray(size!!)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)
            val jsonObject = JSONObject(json)
            val cinemasArray = jsonObject.getJSONArray("cinemas")

            for (i in 0 until cinemasArray.length()) {
                val cinemaObject = cinemasArray.getJSONObject(i)
                val cinemaName = cinemaObject.getString("cinema_name")
                cinemas.add(cinemaName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cinemas
    }

    fun verificarCinemaExiste(nome: String, context: Context?): Boolean {
        val cinemas = readCinemasFromJson(context)
        return cinemas.contains(nome)
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

}