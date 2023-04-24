package com.example.projetofinalpart1.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentRegistBinding
import com.example.projetofinalpart1.model.ObjetoFilme
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegistFragment : Fragment() {
    private lateinit var binding: FragmentRegistBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    val imageList = ArrayList<String>()

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
            val fotos = imageList

            val filme = ObjetoFilme.registarFilme(
                nomeFilme,
                nomeCinema,
                avaliacao,
                data,
                observacoes,
                fotos
            )

            if (filme) {
                if (ObjetoFilme.filmesVistos(nomeFilme)) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(getString(R.string.mensagemRegistado, nomeFilme))
                        .setPositiveButton(
                            getString(R.string.confirmar),
                            DialogInterface.OnClickListener { dialog, which ->
                            })
                    builder.create().show()
                } else {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(getString(R.string.mensagemConfirma, nomeFilme))
                        .setPositiveButton(
                            getString(R.string.confirmar),
                            DialogInterface.OnClickListener { dialog, which ->
                                ObjetoFilme.adicionarListaVistos(
                                    nomeFilme,
                                    nomeCinema,
                                    avaliacao,
                                    data,
                                    observacoes,
                                    fotos
                                )
                                removerCampos()
                            })
                        .setNegativeButton(getString(R.string.cancelar), null)
                    builder.create().show()
                }

            } else {
                val errorMessage = getString(R.string.erroRegistoFilme)
                if (!ObjetoFilme.verificarNomeCinema(nomeCinema)) {
                    binding.cinemaEditText.error = errorMessage
                }
                if (!ObjetoFilme.verificarData(data)) {
                    binding.dataEditText.error = errorMessage
                }
                if (!ObjetoFilme.verificarNomeFilme(nomeFilme)) {
                    binding.nomeFilmeEditText.error = errorMessage
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.camposPorPreencher),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (!ObjetoFilme.percorrerFilmes(nomeFilme)) {
                    binding.nomeFilmeEditText.error = getString(R.string.filmeNaoExiste)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.filmeNaoExiste),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.camposPorPreencher),
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }

        binding.tirarFotoButton.setOnClickListener {
            val options = arrayOf<CharSequence>(
                getString(R.string.tirarFoto),
                getString(R.string.selecionarGaleria),
                getString(R.string.cancelar)
            )
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.adicionarFoto))

            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == getString(R.string.tirarFoto) -> {
                        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                        }
                    }
                    options[item] == getString(R.string.selecionarGaleria) -> {
                        val intent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, 2)
                    }
                    options[item] == getString(R.string.cancelar) -> {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()
        }


        binding.dataEditText.setOnClickListener {
            var hoje = Calendar.getInstance()
            val datePicker = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
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
            dialog.datePicker.maxDate = hoje.timeInMillis
            dialog.show()
            binding.dataEditText.error = null
        }

        binding.avaliacaoSlider.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
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
        Toast.makeText(
            requireContext(),
            getString(R.string.sucesso_registo_filme),
            Toast.LENGTH_LONG
        ).show()
        ObjetoFilme.limparCampos();

        binding.nomeFilmeEditText.setText(ObjetoFilme.nomeFilm)
        binding.cinemaEditText.setText(ObjetoFilme.cinema)
        binding.avaliacaoSlider.progress = ObjetoFilme.avaliacaoFilme.toIntOrNull() ?: 0
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

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val image = data?.extras?.get("data") as Bitmap
                    val photoFile = saveImage(image)
                    imageList.add(photoFile.absolutePath)
                }
                2 -> {
                    val selectedImage = data?.data
                    val image = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        selectedImage
                    )
                    val photoFile = saveImage(image)
                    imageList.add(photoFile.absolutePath)
                }
            }
        }
    }

    private fun saveImage(bitmap: Bitmap): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageName = "IMG_$timeStamp.jpg"
        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(directory, imageName)
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return imageFile
    }


}

