package com.example.projetofinalpart1.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.databinding.TendenciasItemBinding
import com.example.projetofinalpart1.listaTodosFilmes
import com.example.projetofinalpart1.model.Filme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class TendeciasAdapter(
    private val onClick: (String) -> Unit,
    private var items: List<Filme> = listOf()
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
        holder.itemView.setOnClickListener { onClick(items[position].uuid) }
        val url = items[position].poster
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = getBitmapFromURL(url)
            if (bitmap != null) {
                holder.movieImage.setImageBitmap(bitmap)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Filme>) {
        items = newItems
        notifyDataSetChanged()
    }

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
}

