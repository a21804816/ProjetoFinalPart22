package com.example.projetofinalpart1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.databinding.FilmeItemBinding
import com.example.projetofinalpart1.databinding.TendenciasItemBinding
import com.example.projetofinalpart1.model.Filme

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
        holder.movieImage.setImageResource(items[position].imagemCartaz)
    }

    override fun getItemCount(): Int = items.size
}

