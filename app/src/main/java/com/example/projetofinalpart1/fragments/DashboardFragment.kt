package com.example.projetofinalpart1.fragments

import FilmeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.adapters.TendeciasAdapter
import com.example.projetofinalpart1.databinding.FragmentDashboardBinding
import com.example.projetofinalpart1.model.listaFilmesVistos

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_dashboard, container, false
        )
        binding = FragmentDashboardBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FilmeAdapter(listaFilmesVistos) { filme ->

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

            NavigationManager.goToDetalhesFragment(requireActivity().supportFragmentManager, bundle)
        }

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        val movies = listOf(
            R.drawable.movie1,
            R.drawable.movie2,
            R.drawable.movie3
        )

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesList.layoutManager = layoutManager
        binding.moviesList.adapter = TendeciasAdapter(movies)
    }

}
