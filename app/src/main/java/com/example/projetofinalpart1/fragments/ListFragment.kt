package com.example.projetofinalpart1.fragments

import FilmeAdapter
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R

import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.example.projetofinalpart1.model.listaFilmesVistos

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val REQUEST_CODE_SPEECH_INPUT = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_list, container, false
        )
        binding = FragmentListBinding.bind(view)

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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = listaFilmesVistos.filter {
                    it.nomeFilme.contains(newText ?: "")
                }
                val adapter = FilmeAdapter(filteredList) { filme ->

                    val bundle = Bundle().apply {
                        putString("nomeFilme", filme.nomeFilme)
                        putString("nomeCinema", filme.nomeCinema)
                        putString("avaliacao", filme.avaliacao)
                        putString("dataVisualizacao", filme.dataVisualizacao)
                        putString("observacoes", filme.observacoes)
                        putString("fotogradia", filme.fotografia.toString())
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
                binding.recyclerView.adapter = adapter
                return true
            }

        })

        binding.fabMicrophone.setOnClickListener {
            val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechRecognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            try {
                startActivityForResult(speechRecognizerIntent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao reconhecer fala.", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            binding.searchView.setQuery(result, true)
        }
    }

}



