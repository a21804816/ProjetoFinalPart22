package com.example.projetofinalpart1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.databinding.TendenciasItemBinding

class TendeciasAdapter(val movies: List<Int>): RecyclerView.Adapter<TendeciasAdapter.MovieViewHolder>() {
    private lateinit var binding: TendenciasItemBinding

    inner class MovieViewHolder(binding: TendenciasItemBinding): RecyclerView.ViewHolder(binding.root) {
        val movieImage: ImageView = binding.movieImage
        val movieImage2: ImageView = binding.movieImage2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TendenciasItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieImage.setImageResource(movies[position])
        holder.movieImage2.setImageResource(movies[position]+1)
    }

    override fun getItemCount(): Int = movies.size


}

