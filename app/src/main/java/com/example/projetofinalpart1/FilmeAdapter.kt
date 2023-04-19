import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.Filme
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R

import com.example.projetofinalpart1.databinding.FilmeItemBinding

class FilmeAdapter(private var filmes: List<Filme>, private val onFilmeItemClick: (Filme) -> Unit) : RecyclerView.Adapter<FilmeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmeItemBinding.inflate(inflater, parent, false)
        return FilmeViewHolder(binding, onFilmeItemClick)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = filmes[position]
        holder.bind(filme)
    }

    override fun getItemCount(): Int = filmes.size
}

class FilmeViewHolder(private val binding: FilmeItemBinding, private val onFilmeItemClick: (Filme) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(filme: Filme) {
        binding.nomeFilmeEditText.text = filme.nomeFilme
        binding.cinemaEditText.text = filme.nomeCinema
        binding.avaliacaoValor.text = filme.avaliacao
        binding.dataEditText.text = filme.dataVisualizacao
        binding.observacoesEditText.text = filme.observacoes

        itemView.setOnClickListener {
            onFilmeItemClick(filme)
        }
    }
}
