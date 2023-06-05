package com.example.projetofinalpart1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.adapters.FotosDetalhesAdapter
import com.example.projetofinalpart1.data.FilmeRoom
import com.example.projetofinalpart1.data.FilmsDatabase
import com.example.projetofinalpart1.databinding.FragmentDetalhesBinding
import com.example.projetofinalpart1.model.Filme
import kotlinx.coroutines.*

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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        super.onStart()
        objetoFilme = FilmeRoom(FilmsDatabase.getInstance(requireContext()).filmDao())
        binding.nomeFilme.isEnabled = false
        binding.nomeCinema.isEnabled = false
        binding.avaliacao.isEnabled = false
        binding.dataVisualizacao.isEnabled = false
        binding.observacoes.isEnabled = false

        CoroutineScope(Dispatchers.Main).launch {
            filmeUuid?.let {
                objetoFilme.getFilmByUUID(it) { film ->
                    if (film != null) {
                        requireActivity().runOnUiThread {
                            placeData(film)
                            if (film.userToSee) {
                                binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_24)
                            } else {
                                binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_not_24)
                            }
                        }
                    }
                }
            }
        }

        (binding.voltarButton).setOnClickListener {
            requireActivity().onBackPressed()
        }

        (binding.paraVerButton).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                filmeUuid?.let {
                    objetoFilme.getFilmByUUID(it) { film ->
                        if (film != null) {
                            if (!film.userToSee) {
                                objetoFilme.updateFilmToSee(filmeUuid!!,true)
                                binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_24)
                                GlobalScope.launch(Dispatchers.Main) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Filme adicionado à lista para ver",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            } else {
                                objetoFilme.updateFilmToSee(filmeUuid!!,false)
                                binding.paraVerButton.setBackgroundResource(R.drawable.baseline_turned_in_not_24)
                                GlobalScope.launch(Dispatchers.Main) {

                                    Toast.makeText(
                                        requireContext(),
                                        "Filme removido da lista para ver",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.editButton.setOnClickListener {
            binding.avaliacao.isEnabled = true
            binding.observacoes.isEnabled = true

            binding.editButton.setBackgroundResource(R.drawable.baseline_check_circle_24)

            binding.editButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    objetoFilme.updateFilm(filmeUuid!!,binding.avaliacao.text.toString(),binding.observacoes.text.toString())
                }

                binding.avaliacao.isEnabled = false
                binding.dataVisualizacao.isEnabled = false
                Toast.makeText(
                    requireContext(),
                    "Filme editado",
                    Toast.LENGTH_LONG
                ).show()

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
        val fotos = ui.userPhotos

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fotosLista.layoutManager = layoutManager
        binding.fotosLista.adapter = FotosDetalhesAdapter(fotos.toList(), ui.poster)

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





