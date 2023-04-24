package com.example.projetofinalpart1.fragments

import FilmeAdapter
import android.annotation.SuppressLint
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
import com.example.projetofinalpart1.listaTodosFilmes
import com.example.projetofinalpart1.model.listaFilmesVistos

class DashboardFragment : Fragment() {
    val filmesOrdenados = listaTodosFilmes.sortedByDescending { it.avaliacaoImdb.toDouble() }.take(10)
    private val adapter = TendeciasAdapter(::onOperationClick, listaTodosFilmes)
    val adapterOrder = TendeciasAdapter(::onOperationClick, filmesOrdenados)
    private val adapterVistos = FilmeAdapter(::onOperationClick, listaFilmesVistos)


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

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.moviesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesList.adapter = adapter
        binding.moviesList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        if (listaFilmesVistos.isEmpty()) {
            binding.texto2.text=getString(R.string.topFilmes)
            binding.vistosMoviesList.visibility = View.GONE
            binding.orderMoviesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.orderMoviesList.adapter = adapterOrder
            binding.orderMoviesList.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }else{
            binding.texto2.text=getString(R.string.listaVistos)
            binding.orderMoviesList.visibility = View.GONE
            binding.vistosMoviesList.layoutManager = LinearLayoutManager(requireContext())
            binding.vistosMoviesList.adapter = adapterVistos
            binding.vistosMoviesList.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

}
