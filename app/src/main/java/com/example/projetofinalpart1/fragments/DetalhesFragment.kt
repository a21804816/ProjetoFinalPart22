package com.example.projetofinalpart1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.adapters.FotosDetalhesAdapter
import com.example.projetofinalpart1.databinding.FragmentDetalhesBinding
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.ObjetoFilme
import com.example.projetofinalpart1.model.listaFilmesParaVer

private const val ARG_FILME_UUID = "ARG_FILME_UUID"

class DetalhesFragment : Fragment() {

    private lateinit var binding: FragmentDetalhesBinding

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
        val view = inflater.inflate(R.layout.fragment_detalhes, container, false)
        binding = FragmentDetalhesBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.nomeFilme.isEnabled = false
        binding.nomeCinema.isEnabled = false
        binding.avaliacao.isEnabled = false
        binding.dataVisualizacao.isEnabled = false
        binding.observacoes.isEnabled = false

        filmeUuid?.let { uuid ->
            val operation = ObjetoFilme.getOperationById(uuid)
            operation?.let { placeData(it) }
            if (operation != null) {
                if (operation.paraVer) {
                    binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_24)
                } else {
                    binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_not_24)
                }
            }
        }

        (binding.voltarButton).setOnClickListener {
            requireActivity().onBackPressed()
        }

        (binding.paraVerButton).setOnClickListener {
            val filme = ObjetoFilme.getOperationById(filmeUuid!!)
            if (filme != null) {
                if (filme.paraVer) {
                    listaFilmesParaVer.remove(filme)
                    binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_not_24)
                    filme.paraVer = false
                } else {
                    listaFilmesParaVer.add(filme)
                    binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_24)
                    filme.paraVer = true
                }
            }
        }

        binding.editButton.setOnClickListener {
            binding.nomeFilme.isEnabled = true
            binding.nomeCinema.isEnabled = true
            binding.avaliacao.isEnabled = true
            binding.dataVisualizacao.isEnabled = true
            binding.observacoes.isEnabled = true



            binding.editButton.setBackgroundResource(R.drawable.baseline_check_circle_24)

            binding.editButton.setOnClickListener {
                val filme = ObjetoFilme.getOperationById(filmeUuid!!)
                if (filme != null) {
                    filme.nomeFilme = binding.nomeFilme.text.toString()
                    filme.nomeCinema = binding.nomeCinema.text.toString()
                    filme.avaliacao = binding.avaliacao.text.toString()
                    filme.dataVisualizacao = binding.dataVisualizacao.text.toString()
                    filme.observacoes = binding.observacoes.text.toString()
                }

                binding.nomeFilme.isEnabled = false
                binding.nomeCinema.isEnabled = false
                binding.avaliacao.isEnabled = false
                binding.dataVisualizacao.isEnabled = false

                binding.editButton.setBackgroundResource(R.drawable.ic_baseline_edit_24)
            }
        }

    }


    private fun placeData(ui: Filme) {
        binding.nomeFilme.setText(ui.nomeFilme)
        binding.genero.text = ui.genero
        binding.sinopse.text = ui.sinopse
        binding.dataLancamento.text = ui.dataLancamento
        binding.avaliacaoImdb.text = ui.avaliacaoImdb
        binding.linkImdb.text = ui.linkImdb
        if (ui.avaliado) {
            binding.nomeCinema.setText(ui.nomeCinema)
            binding.avaliacao.setText(ui.avaliacao)
            binding.dataVisualizacao.setText(ui.dataVisualizacao)
            binding.observacoes.setText(ui.observacoes)
        } else {
            binding.nomeCinemaText.visibility = View.GONE
            binding.nomeCinema.visibility = View.GONE
            binding.avaliacao.visibility = View.GONE
            binding.avaliacaoText.visibility = View.GONE
            binding.dataVisualizacaoText.visibility = View.GONE
            binding.dataVisualizacao.visibility = View.GONE
            binding.observacoesText.visibility = View.GONE
            binding.observacoes.visibility = View.GONE
        }
        val fotos = ui.fotografia

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fotosLista.layoutManager = layoutManager
        binding.fotosLista.adapter = FotosDetalhesAdapter(fotos, ui.imagemCartaz)

    }

    companion object {

        @JvmStatic
        fun newInstance(uuid: String) =
            DetalhesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FILME_UUID, uuid)
                }
            }
    }


}





