package com.example.projetofinalpart1.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.data.ConnectivityUtil
import com.example.projetofinalpart1.databinding.TendenciasItemBinding
import com.example.projetofinalpart1.model.FilmeDashboard
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

class TendeciasAdapter(
    private val onClick: (String) -> Unit,
    private var items: List<FilmeDashboard> = listOf()
) : RecyclerView.Adapter<TendeciasAdapter.TodosViewHolder>() {

    inner class TodosViewHolder(binding: TendenciasItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val movieImage: ImageView = binding.movieImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        return TodosViewHolder(
            TendenciasItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick(items[position].imdbID) }
        val url = items[position].poster
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = getBitmapFromURL(holder.movieImage.context, url)
            if (bitmap != null) {
                holder.movieImage.setImageBitmap(bitmap)
            } else {
                holder.movieImage.setImageResource(R.drawable.placeholder_image)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<FilmeDashboard>) {
        items = newItems
        notifyDataSetChanged()
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
}

