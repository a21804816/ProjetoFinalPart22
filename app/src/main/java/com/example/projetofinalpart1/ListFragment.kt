package com.example.projetofinalpart1

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

import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayout

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

        val adapter = FilmeAdapter(listaFilmes) { filme ->

            val bundle = Bundle().apply {
                putString("nomeFilme", filme.nomeFilme)
                putString("nomeCinema", filme.nomeCinema)
                putString("avaliacao", filme.avaliacao)
                putString("dataVisualizacao", filme.dataVisualizacao)
                putString("observacoes", filme.observacoes)
            }

            NavigationManager.goToDetalhesFragment(requireActivity().supportFragmentManager, bundle)
        }

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lista"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Mapa"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {

                    }
                    1 -> {
                        NavigationManager.goToMapFragment(requireActivity().supportFragmentManager)
                        binding.tabLayout.getTabAt(1)?.select()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = listaFilmes.filter {
                    it.nomeFilme.contains(newText ?: "")
                }
                val adapter = FilmeAdapter(filteredList) { filme ->

                    val bundle = Bundle().apply {
                        putString("nomeFilme", filme.nomeFilme)
                        putString("nomeCinema", filme.nomeCinema)
                        putString("avaliacao", filme.avaliacao)
                        putString("dataVisualizacao", filme.dataVisualizacao)
                        putString("observacoes", filme.observacoes)
                    }

                    NavigationManager.goToDetalhesFragment(requireActivity().supportFragmentManager, bundle)
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



