package com.example.projetofinalpart1.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.databinding.FotosDetalhesItemBinding

class FotosDetalhesAdapter(val fotos: List<String>, val imagemCartaz: Int): RecyclerView.Adapter<FotosDetalhesAdapter.FotosViewHolder>() {
    inner class FotosViewHolder(binding: FotosDetalhesItemBinding): RecyclerView.ViewHolder(binding.root) {
        val images: ImageView = binding.fotos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FotosDetalhesItemBinding.inflate(inflater, parent, false)
        return FotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FotosViewHolder, position: Int) {
        if (position == 0) {
            holder.images.setImageResource(imagemCartaz)
        } else {
            holder.images.setImageURI(Uri.parse(fotos[position - 1]))
        }
    }
    override fun getItemCount(): Int = fotos.size + 1
}

