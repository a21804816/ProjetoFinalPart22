package com.example.projetofinalpart1.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.data.ConnectivityUtil
import com.example.projetofinalpart1.databinding.FotosDetalhesItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FotosDetalhesAdapter(val fotos: List<String>, val imagemCartaz: String) :
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

    override fun getItemCount(): Int {
        return fotos.size+1
    }

    override fun onBindViewHolder(holder: FotosViewHolder, position: Int) {
        if (position == 0) {
            val url = imagemCartaz
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = getBitmapFromURL(holder.images.context, url)
                if (bitmap != null) {
                    holder.images.setImageBitmap(bitmap)
                }
            }
        } else {
            val filePath = fotos[position-1]
            val cleanedList = filePath.replace("[", "").replace("]", "")
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = getBitmapFromFilePath(cleanedList)
                if (bitmap != null) {
                    holder.images.setImageBitmap(bitmap)
                }
            }

        }
    }

    private suspend fun getBitmapFromURL(context: Context, src: String): Bitmap? {
        val fileName = getFileNameFromURL(src)
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)

        return if (file.exists()) {
            decodeBitmapFromFile(file)
        } else {
            if (ConnectivityUtil.isOnline(context)) {
                withContext(Dispatchers.IO) {
                    try {
                        val url = URL(src)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.doInput = true
                        connection.connect()
                        val inputStream = connection.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        saveBitmapToCache(context, bitmap, fileName)
                        bitmap
                    } catch (e: IOException) {
                        e.printStackTrace()
                        null
                    }
                }
            } else {
                null
            }
        }
    }

    private fun saveBitmapToCache(context: Context, bitmap: Bitmap, fileName: String) {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)

        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun decodeBitmapFromFile(file: File): Bitmap? {
        return try {
            val inputStream = FileInputStream(file)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileNameFromURL(url: String): String {
        return url.substringAfterLast("/")
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

