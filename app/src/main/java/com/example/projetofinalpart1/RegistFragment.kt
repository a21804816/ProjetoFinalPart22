package com.example.projetofinalpart1

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast

import com.example.projetofinalpart1.databinding.FragmentRegistBinding
import java.text.SimpleDateFormat
import java.util.*

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
        binding.nomeFilmeEditText
        binding.registarButton.setOnClickListener {
            val nomeFilme = binding.nomeFilmeEditText.text.toString()
            val nomeCinema = binding.cinemaEditText.text.toString()
            val avaliacao = binding.avaliacaoSlider.progress.toString()
            val data = binding.dataEditText.text.toString()
            val observacoes = binding.observacoesEditText.text.toString()
            val foto = binding.tirarFotoButton.toString()
            val filme = registarFilme(nomeFilme, nomeCinema, avaliacao, data, observacoes, foto)

            if (filme) {
                Toast.makeText(requireContext(), "Filme registado com sucesso!", Toast.LENGTH_LONG).show()
                binding.nomeFilmeEditText.text.clear()
                binding.cinemaEditText.text.clear()
                binding.avaliacaoSlider.progress = 5
                binding.dataEditText.text=""
                binding.observacoesEditText.text.clear()
            } else {
                val errorMessage = getString(R.string.erroRegistoFilme)
                if(!verificarNomeFilme(nomeFilme)){
                    binding.nomeFilmeEditText.error = errorMessage
                }
                if(!verificarNomeCinema(nomeCinema)){
                    binding.cinemaEditText.error = errorMessage
                }
                if(!verificarData(data)){
                    binding.dataEditText.error = errorMessage
                }
                Toast.makeText(requireContext(), "Estão campos por preencher", Toast.LENGTH_LONG).show()
            }
        }

        val calendario = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calendario.set(Calendar.YEAR, year)
            calendario.set(Calendar.MONTH, month)
            calendario.set(Calendar.DAY_OF_MONTH, day)
            updateLable(calendario)
        }

        binding.dataEditText.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.avaliacaoSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.avaliacaoValor.text = "$progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateLable(calendario: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sd = SimpleDateFormat(myFormat, Locale.UK)
        binding.dataEditText.text = sd.format(calendario.time)
    }

}

