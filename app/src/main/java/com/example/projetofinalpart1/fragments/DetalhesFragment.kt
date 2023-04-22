package com.example.projetofinalpart1.fragments

import DetalhesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentDetalhesBinding
import com.example.projetofinalpart1.model.listaFilmesVistos

class DetalhesFragment : Fragment() {

    private lateinit var binding: FragmentDetalhesBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
            val view = inflater.inflate(
                R.layout.fragment_detalhes, container, false
            )
            binding = FragmentDetalhesBinding.bind(view)

            val adapter = DetalhesAdapter(listaFilmesVistos) { filme ->

                val bundle = Bundle().apply {
                    putString("nomeFilme", filme.nomeFilme)
                    putString("nomeCinema", filme.nomeCinema)
                    putString("avaliacao", filme.avaliacao)
                    putString("dataVisualizacao", filme.dataVisualizacao)
                    putString("observacoes", filme.observacoes)
                    putString("fotografia", filme.fotografia.toString())
                    putString("imagemCartaz", filme.imagemCartaz.toString())
                    putString("genero", filme.genero)
                    putString("sinopse", filme.sinopse)
                    putString("dataLancamento", filme.dataLancamento)
                    putString("avaliacaoImdb", filme.avaliacaoImdb)
                    putString("linkImdb", filme.linkImdb)

                }

                NavigationManager.goToDetalhesFragment(
                    requireActivity().supportFragmentManager,
                    bundle
                )
            }

            binding.recyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
            }
            return binding.root
        }
}





