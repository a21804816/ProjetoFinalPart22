package com.example.projetofinalpart1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinalpart1.databinding.FragmentRegistBinding

class RegistFragment : Fragment() {

    private lateinit var binding: FragmentRegistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_regist, container, false
        )
        binding = FragmentRegistBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.registarButton.setOnClickListener {
            var filme = Filme.registarFilme(binding.nomeFilmeEditText.text.toString(),
                binding.cinemaEditText.text.toString(),
                binding.avaliacaoSlider.toString(),
                binding.dataEditText.toString(),
                binding.observacoesEditText.text.toString(),
                binding.tirarFotoButton.toString()
            )
            listaFilmes.add(filme)

        }
    }

}

