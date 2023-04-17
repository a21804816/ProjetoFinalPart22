import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.Filme

import com.example.projetofinalpart1.databinding.FilmeItemBinding

class FilmeAdapter(private var filmes: List<Filme>) : RecyclerView.Adapter<FilmeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmeItemBinding.inflate(inflater, parent, false)
        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = filmes[position]
        holder.bind(filme)
    }

    override fun getItemCount(): Int = filmes.size
}

class FilmeViewHolder(private val binding: FilmeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(filme: Filme) {
        binding.nomeFilmeEditText.text = filme.nomeFilme
        binding.cinemaEditText.text = filme.nomeCinema
        binding.avaliacaoValor.text = filme.avaliacao
        binding.dataEditText.text = filme.dataVisualizacao
        binding.observacoesEditText.text = filme.observacoes


    }
}