package com.example.projetofinalpart1.fragments

import FilmeAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.example.projetofinalpart1.model.listaFilmesVistos

class ParaVerFragment : Fragment() {
    private val adapter = FilmeAdapter(::onOperationClick, listaFilmesVistos)
    private lateinit var binding: FragmentListBinding
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (listaFilmesVistos.isEmpty()) {
            binding.textEmptyList!!.visibility = View.VISIBLE
        } else {
            binding.textEmptyList!!.visibility = View.GONE
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }


        binding.fabMicrophone.setOnClickListener {
            val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechRecognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            try {
                startActivityForResult(speechRecognizerIntent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao reconhecer fala.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            binding.searchView.setQuery(result, true)
        }
    }
}