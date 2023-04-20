import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.Filme
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R

import com.example.projetofinalpart1.databinding.FilmeItemBinding
import com.example.projetofinalpart1.listaFilmes

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
        binding.filmeCardView.addView(filme.fotografia,0)

        val orientation = itemView.resources.configuration.orientation


        binding.cinemaEditText.visibility = if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE
        binding.avaliacaoValor.visibility = if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE
        binding.observacoesEditText.visibility = if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE


        itemView.setOnClickListener {
            onFilmeItemClick(filme)
        }
    }
}

private fun CardView.addView(fotografia: ArrayList<Bitmap>, i: Int) {

}
