package com.example.projetofinalpart1.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.databinding.FotosDetalhesItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FotosDetalhesAdapter(val fotos: List<Char>, val imagemCartaz: String) :
    RecyclerView.Adapter<FotosDetalhesAdapter.FotosViewHolder>() {
    inner class FotosViewHolder(binding: FotosDetalhesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val images: ImageView = binding.fotos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FotosDetalhesItemBinding.inflate(inflater, parent, false)
        return FotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FotosViewHolder, position: Int) {
        if (position == 0) {
            val url = imagemCartaz
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = getBitmapFromURL(url)
                if (bitmap != null) {
                    holder.images.setImageBitmap(bitmap)
                }
            }
        } else {
            val filePath = fotos[position - 1]
            holder.images.setImageURI(Uri.parse(filePath.toString()))
        }
    }



    override fun getItemCount(): Int = fotos.size + 1

    private suspend fun getBitmapFromURL(src: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(src)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun getBitmapFromFilePath(filePath: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(filePath)
                BitmapFactory.decodeFile(file.absolutePath)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

}

