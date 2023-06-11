package com.example.projetofinalpart1.fragments

import FilmeAdapter
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.data.FilmeRepository
import com.example.projetofinalpart1.databinding.DialogLayoutBinding
import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.listaFilmesParaVer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParaVerFragment : Fragment() {
    private val adapter = FilmeAdapter(::onOperationClick, listaFilmesParaVer)
    private lateinit var binding: FragmentListBinding
    private val REQUEST_CODE_SPEECH_INPUT = 100
    val repository = FilmeRepository.getInstance()
    private val filmList = mutableListOf<Filme>()
    private val filteredFilmList = mutableListOf<Filme>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            repository.getFilmToSeeList { result ->
                if (result.isSuccess) {
                    CoroutineScope(Dispatchers.Main).launch {
                        filmList.clear()
                        filmList.addAll(result.getOrDefault(emptyList()))
                        filteredFilmList.clear()
                        filteredFilmList.addAll(filmList)
                        adapter.setData(filteredFilmList)
                    }
                }
            }
            repository.getFilmToSeeListDashboard { result ->
                if (result.isSuccess) {
                    CoroutineScope(Dispatchers.Main).launch {
                        filmList.clear()
                        filmList.addAll(result.getOrDefault(emptyList()))
                        filteredFilmList.addAll(filmList)
                        adapter.setData(filteredFilmList)
                    }
                }
            }
        }



        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredFilmList.clear()
                if (!newText.isNullOrEmpty()) {
                    filteredFilmList.addAll(filmList.filter { filme ->
                        filme.title.contains(newText, ignoreCase = true)
                    })
                } else {
                    filteredFilmList.addAll(filmList)
                }
                adapter.setData(filteredFilmList)
                return true
            }
        })

        binding.fabMicrophone.setOnClickListener {
            val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_INPUT)

        }
    }

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.searchView.setQuery(result?.get(0), true)

        }
    }
}