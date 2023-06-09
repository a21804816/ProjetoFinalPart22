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
import com.example.projetofinalpart1.data.FilmeRepository
import com.example.projetofinalpart1.databinding.FragmentDashboardBinding
import com.example.projetofinalpart1.listaTodosFilmes
import com.example.projetofinalpart1.model.listaFilmesVistos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    val filmesOrdenados = listaTodosFilmes.sortedByDescending { it.imdbRating.toDouble() }.take(10)
    private val adapter = TendeciasAdapter(::onOperationClick, listaTodosFilmes)
    val adapterOrder = TendeciasAdapter(::onOperationClick, filmesOrdenados)
    val repository = FilmeRepository.getInstance()

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

        CoroutineScope(Dispatchers.IO).launch {
            repository.getFilmList { result ->
                if(result.isSuccess) {
                    CoroutineScope(Dispatchers.Main).launch {
                        adapter.setData(result.getOrDefault(mutableListOf()))
                        binding.moviesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.moviesList.adapter = adapter
                        binding.moviesList.apply {
                            this.adapter = adapter
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        }
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            repository.getFilmListOrder { result ->
                if(result.isSuccess) {
                    val top10Films = result.getOrDefault(mutableListOf()).take(10)
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterOrder.setData(top10Films)
                        binding.texto2.text = getString(R.string.topFilmes)
                        binding.orderMoviesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.orderMoviesList.adapter = adapterOrder
                        binding.orderMoviesList.apply {
                            this.adapter = adapterOrder
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        }
                    }
                }
            }
        }

    }

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

}
