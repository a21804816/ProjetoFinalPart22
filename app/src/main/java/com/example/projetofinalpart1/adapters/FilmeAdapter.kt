import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.model.Filme

import com.example.projetofinalpart1.databinding.FilmeItemBinding

class FilmeAdapter(private val onClick: (String) -> Unit,
                   private var items: List<Filme> = listOf()
) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    class FilmeViewHolder(val binding: FilmeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        return FilmeViewHolder(
            FilmeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick(items[position].uuid) }
        holder.binding.nomeFilmeEditText.text = items[position].nomeFilme
        holder.binding.cinemaEditText.text = items[position].nomeCinema
        holder.binding.avaliacaoValor.text = items[position].avaliacao
        holder.binding.dataEditText.text = items[position].dataVisualizacao
        holder.binding.observacoesEditText.text = items[position].observacoes
        holder.binding.filmeFotografiaImageView.setImageResource(items[position].imagemCartaz)
    }
    override fun getItemCount(): Int = items.size

}

