import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.model.Filme

import com.example.projetofinalpart1.databinding.DelhatesFilmeBinding

class DetalhesAdapter(private var filmes: List<Filme>, private val onFilmeItemClick: (Filme) -> Unit) : RecyclerView.Adapter<DetalhesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalhesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DelhatesFilmeBinding.inflate(inflater, parent, false)
        return DetalhesViewHolder(binding, onFilmeItemClick)
    }

    override fun onBindViewHolder(holder: DetalhesViewHolder, position: Int) {
        val filme = filmes[position]
        holder.bind(filme)
    }

    override fun getItemCount(): Int = filmes.size


}

class DetalhesViewHolder(private val binding: DelhatesFilmeBinding, private val onFilmeItemClick: (Filme) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(filme: Filme) {
        binding.nomeFilmeTextView.text = "Nome do Filme: ${filme.nomeFilme}"
        binding.nomeCinemaTextView.text = "Nome do Cinema: ${filme.nomeCinema}"
        binding.avaliacaoTextView.text = "Avaliação: ${filme.avaliacao}"
        binding.dataVisualizacaoTextView.text = "Data de visualização: ${filme.dataVisualizacao}"
        binding.observacoesTextView.text = "Observações: ${filme.observacoes}"
        binding.fotografiaTextView.text = "Fotografia: ${filme.fotografia}"
        binding.imagemCartazImageView.setImageResource(filme.imagemCartaz)
        binding.generoTextView.text = "Gênero: ${filme.genero}"
        binding.sinopseTextView.text = "Sinopse: ${filme.sinopse}"
        binding.dataLancamentoTextView.text = "Data de lançamento: ${filme.dataLancamento}"
        binding.avaliacaoImdbTextView.text = "Avaliação do IMDb: ${filme.avaliacaoImdb}"
        binding.linkImdbTextView.text = "Link do IMDb: ${filme.linkImdb}"

        binding.voltarButton.setOnClickListener{
            val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
            fragmentManager.popBackStack()
        }
    }
}
