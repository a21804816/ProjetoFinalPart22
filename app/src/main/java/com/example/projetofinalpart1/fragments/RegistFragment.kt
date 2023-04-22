package com.example.projetofinalpart1.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.SeekBar
import android.widget.Toast
import com.example.projetofinalpart1.R

import com.example.projetofinalpart1.databinding.FragmentRegistBinding
import com.example.projetofinalpart1.model.ObjetoFilme
import java.util.*

class RegistFragment : Fragment() {
    private lateinit var binding: FragmentRegistBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    val imageList = ArrayList<Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_regist, container, false
        )
        binding = FragmentRegistBinding.bind(view)
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onStart() {
        super.onStart()
        binding.nomeFilmeEditText
        binding.registarButton.setOnClickListener {
            val nomeFilme = binding.nomeFilmeEditText.text.toString()
            val nomeCinema = binding.cinemaEditText.text.toString()
            val avaliacao = binding.avaliacaoSlider.progress.toString()
            val data = binding.dataEditText.text.toString()
            val observacoes = binding.observacoesEditText.text.toString()
            val fotos= imageList

            val filme = ObjetoFilme.registarFilme(nomeFilme, nomeCinema, avaliacao, data, observacoes, fotos)

            if (filme) {
                removerCampos()
            } else {
                val errorMessage = getString(R.string.erroRegistoFilme)
                if(!ObjetoFilme.verificarNomeCinema(nomeCinema)){
                    binding.cinemaEditText.error = errorMessage
                }
                if(!ObjetoFilme.verificarData(data)){
                    binding.dataEditText.error = errorMessage
                }
                if(!ObjetoFilme.verificarNomeFilme(nomeFilme)){
                    binding.nomeFilmeEditText.error = errorMessage
                    Toast.makeText(requireContext(), "Estão campos por preencher", Toast.LENGTH_LONG).show()
                } else if(!ObjetoFilme.percorrerFilmes(nomeFilme)){
                    binding.nomeFilmeEditText.error = "Filme não existe"
                    Toast.makeText(requireContext(), "filme não existe", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(requireContext(), "Estão campos por preencher", Toast.LENGTH_LONG).show()

                }

            }
        }

        binding.tirarFotoButton.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        binding.dataEditText.setOnClickListener {
            val datePicker = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                    ObjetoFilme.setCalendario(year, monthOfYear, dayOfMonth)
                    updateLable()
                }
            }
            val dialog = DatePickerDialog(
                requireContext(),
                datePicker,
                ObjetoFilme.calendario.get(Calendar.YEAR),
                ObjetoFilme.calendario.get(Calendar.MONTH),
                ObjetoFilme.calendario.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = ObjetoFilme.calendario.timeInMillis // set max date to current date
            dialog.show()
            binding.dataEditText.error = null
        }

        binding.avaliacaoSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mudarAvaliacao(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun mudarAvaliacao(progress: Int) {
        ObjetoFilme.alterarAvaliacao(progress)
        binding.avaliacaoValor.text = ObjetoFilme.avaliacaoFilme
    }

    private fun removerCampos() {
        Toast.makeText(requireContext(), "Filme registado com sucesso!", Toast.LENGTH_LONG).show()
        ObjetoFilme.limparCampos();

        binding.nomeFilmeEditText.setText(ObjetoFilme.nomeFilm)
        binding.cinemaEditText.setText(ObjetoFilme.cinema)
        binding.avaliacaoSlider.progress= ObjetoFilme.avaliacaoFilme.toIntOrNull() ?:0
        binding.dataEditText.text = ObjetoFilme.data
        binding.observacoesEditText.setText(ObjetoFilme.observacoesFilme)
        imageList.clear()
    }

    private fun updateLable() {
        binding.dataEditText.text = ObjetoFilme.data
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.fotoImageView.setImageBitmap(imageBitmap)
            imageList.add(imageBitmap)
        }
    }

}

