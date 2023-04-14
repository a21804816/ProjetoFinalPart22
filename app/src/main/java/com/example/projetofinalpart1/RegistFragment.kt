package com.example.projetofinalpart1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

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

        binding.registarButton.setOnClickListener {
            val nomeFilme = binding.nomeFilmeEditText.text.toString()
            val nomeCinema = binding.cinemaEditText.text.toString()
            val avaliacao = binding.avaliacaoSlider.progress.toString()
            val data = binding.dataEditText.toString()
            val observacoes = binding.observacoesEditText.text.toString()
            val foto = binding.tirarFotoButton.toString()

            val filme = Filme.registarFilme(nomeFilme, nomeCinema, avaliacao, data, observacoes, foto)

            if (filme) {
                //go to list fragment
            } else {
                if(!Filme.verificarNomeFilme(nomeFilme)){
                    binding.nomeFilmeEditText.setBackgroundColor(Color.RED)
                }
                if(!Filme.verificarNomeCinema(nomeCinema)){
                    binding.cinemaEditText.setBackgroundColor(Color.RED)
                }
                if(!Filme.verificarData(data)){
                    binding.dataEditText.setBackgroundColor(Color.RED)
                }
            }
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, day)
            updateLable(c)
        }

        binding.dataEditText.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.avaliacaoSlider.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.avaliacaoValor.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    private fun updateLable(c: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sd = SimpleDateFormat(myFormat, Locale.UK)
        binding.dataEditText.setText(sd.format(c.time))
    }

}

