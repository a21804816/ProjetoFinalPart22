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

        val listaFilmes = listaFilmesVistos // ou outra lista de filmes que você queira exibir

        val adapter = FilmeAdapter(::onOperationClick, listaFilmes)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
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

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }
}

