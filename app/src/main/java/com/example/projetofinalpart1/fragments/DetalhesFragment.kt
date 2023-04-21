package com.example.projetofinalpart1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.databinding.FragmentDetalhesBinding

class DetalhesFragment : Fragment() {

    private lateinit var binding: FragmentDetalhesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetalhesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nomeFilme = arguments?.getString("nomeFilme")
        val nomeCinema = arguments?.getString("nomeCinema")
        val avaliacao = arguments?.getString("avaliacao")
        val dataVisualizacao = arguments?.getString("dataVisualizacao")
        val observacoes = arguments?.getString("observacoes")


        binding.nomeFilmeTextView.text = nomeFilme
        binding.nomeCinemaTextView.text = nomeCinema
        binding.avaliacaoTextView.text = avaliacao
        binding.dataVisualizacaoTextView.text = dataVisualizacao
        binding.observacoesTextView.text = observacoes

        (binding.voltarButton).setOnClickListener {
            requireActivity().onBackPressed()
        }


    }
}

