package com.example.projetofinalpart1.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentEditarBinding
import com.example.projetofinalpart1.model.ObjetoFilme
import java.util.*



import com.example.projetofinalpart1.NavigationManager

import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.ObjetoFilme.data
import com.example.projetofinalpart1.model.ObjetoFilme.fotos
import kotlin.collections.ArrayList


private const val ARG_FILME_UUID = "ARG_FILME_UUID"

class EditarFragment : Fragment() {

    private lateinit var binding: FragmentEditarBinding

    private var filmeUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmeUuid = it.getString(ARG_FILME_UUID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_editar, container, false)
        binding = FragmentEditarBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        filmeUuid?.let { uuid ->
            val operation = ObjetoFilme.getOperationById(uuid)
            operation?.let { placeData(it) }
        }
        setupButtons()
    }

    private fun setupButtons() {
        binding.guardarButton.setOnClickListener {
            salvarEdicao()
        }
        binding.cancelarButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun salvarEdicao() {
        val nomeFilme = binding.nomeFilmeEditText.text.toString()
        val nomeCinema = binding.cinemaEditText.text.toString()
        val avaliacao = binding.avaliacaoValor.text.toString()
        val dataVisualizacao = binding.dataEditText.text.toString()
        val observacoes = binding.observacoesEditText.text.toString()

        // Verifica se o nome do filme e o nome do cinema foram preenchidos
        if (nomeFilme.isEmpty() || nomeCinema.isEmpty()) {
            binding.nomeFilmeEditText.error = getString(R.string.erroRegistoFilme)
            binding.cinemaEditText.error = getString(R.string.erroRegistoFilme)
            return
        }

        // Cria um novo objeto Filme com as informações editadas
        val filme = ObjetoFilme.registarFilme(
            nomeFilme,
            nomeCinema,
            avaliacao,
            dataVisualizacao,
            observacoes,
            fotos as ArrayList<String>

        )

        // Salva o filme editado no objeto ObjetoFilme
        filmeUuid?.let { uuid ->
            ObjetoFilme.editOperation(uuid, nomeFilme,nomeCinema,avaliacao,dataVisualizacao,observacoes,
                listOf(fotos.toString())
            )
        }

        // Volta para a tela de detalhes do filme
        requireActivity().onBackPressed()
    }


    private fun placeData(ui: Filme) {
        binding.nomeFilmeEditText.setText(ui.nomeFilme)
        binding.cinemaEditText.setText(ui.nomeCinema)
        binding.avaliacaoValor.setText(ui.avaliacao)
        binding.dataEditText.setText(ui.dataVisualizacao)
        binding.observacoesEditText.setText(ui.observacoes)
    }

    companion object {

        @JvmStatic
        fun newInstance(uuid: String) =
            EditarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FILME_UUID, uuid)
                }
            }
    }

}
