package com.example.projetofinalpart1.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentEditarBinding
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.ObjetoFilme

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        filmeUuid?.let { uuid ->
            val filme = ObjetoFilme.getOperationById(uuid)
            filme?.let { placeData(it) }
        }
    }

    private fun placeData(filme: Filme) {
        binding.nomeFilmeEditText.setText(filme.nomeFilme)
        binding.cinemaEditText.setText(filme.nomeCinema)
        binding.avaliacaoValor.setText(filme.avaliacao)
        binding.dataEditText.setText(filme.dataVisualizacao)
        binding.observacoesEditText.setText(filme.observacoes)
    }

    private fun updateFilme() {
        val nomeFilme = binding.nomeFilmeEditText.text.toString()
        val nomeCinema = binding.cinemaEditText.text.toString()
        val avaliacao = binding.avaliacaoValor.text.toString()
        val dataVisualizacao = binding.dataEditText.text.toString()
        val observacoes = binding.observacoesEditText.text.toString()
        filmeUuid?.let {


        }
    }

    override fun onStop() {
        super.onStop()
        updateFilme()
    }

    companion object {
        @JvmStatic
        fun newInstance(filmeUuid: String) =
            EditarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FILME_UUID, filmeUuid)
                }
            }
    }
}

