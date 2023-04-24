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
        filmeUuid?.let { uuid ->
            val operation = ObjetoFilme.getOperationById(uuid)
            operation?.let { placeData(it) }
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

    }


    private fun placeData(ui: Filme) {
        binding.nomeFilme.text = ui.nomeFilme
        binding.genero.text = ui.genero
        binding.sinopse.text = ui.sinopse
        binding.dataLancamento.text = ui.dataLancamento
        binding.avaliacaoImdb.text = ui.avaliacaoImdb
        binding.linkImdb.text = ui.linkImdb
        if (ui.avaliado) {
            binding.nomeCinema.text = ui.nomeCinema
            binding.avaliacao.text = ui.avaliacao
            binding.dataVisualizacao.text = ui.dataVisualizacao
            binding.observacoes.text = ui.observacoes
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





