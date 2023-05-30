package com.example.projetofinalpart1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.adapters.FotosDetalhesAdapter
import com.example.projetofinalpart1.data.FilmeRoom
import com.example.projetofinalpart1.data.FilmsDatabase
import com.example.projetofinalpart1.databinding.FragmentDetalhesBinding
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.listaFilmesParaVer

private const val ARG_FILME_UUID = "ARG_FILME_UUID"

class DetalhesFragment : Fragment() {

    private lateinit var binding: FragmentDetalhesBinding

    private var filmeUuid: String? = null
    private lateinit var objetoFilme: FilmeRoom

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
        objetoFilme = FilmeRoom(FilmsDatabase.getInstance(requireContext()).filmDao())
        binding.nomeFilme.isEnabled = false
        binding.nomeCinema.isEnabled = false
        binding.avaliacao.isEnabled = false
        binding.dataVisualizacao.isEnabled = false
        binding.observacoes.isEnabled = false

        filmeUuid?.let { uuid ->
            val operation = objetoFilme.getOperationById(uuid)
            operation?.let { placeData(it) }
            if (operation != null) {
                if (operation.userToSee) {
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
            val filme = objetoFilme.getOperationById(filmeUuid!!)
            if (filme != null) {
                if (filme.userToSee) {
                    listaFilmesParaVer.remove(filme)
                    binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_not_24)
                    filme.userToSee = false
                } else {
                    listaFilmesParaVer.add(filme)
                    binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_24)
                    filme.userToSee = true
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
                val filme = objetoFilme.getOperationById(filmeUuid!!)
                if (filme != null) {
                    filme.title = binding.nomeFilme.text.toString()
                    filme.userCinema = binding.nomeCinema.text.toString()
                    filme.userRating = binding.avaliacao.text.toString()
                    filme.userDate = binding.dataVisualizacao.text.toString()
                    filme.userObservations = binding.observacoes.text.toString()
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
        binding.nomeFilme.setText(ui.title)
        binding.genero.text = ui.genre
        binding.sinopse.text = ui.plot
        binding.dataLancamento.text = ui.released
        binding.avaliacaoImdb.text = ui.imdbRating
        binding.linkImdb.text = ui.imdbID
        if (ui.userAvaliated) {
            binding.nomeCinema.setText(ui.userCinema)
            binding.avaliacao.setText(ui.userRating)
            binding.dataVisualizacao.setText(ui.userDate)
            binding.observacoes.setText(ui.userObservations)
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
        binding.fotosLista.adapter = FotosDetalhesAdapter(fotos, ui.poster)

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





