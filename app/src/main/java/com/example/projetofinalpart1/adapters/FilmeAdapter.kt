import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.databinding.FilmeItemBinding
import com.example.projetofinalpart1.model.Filme

class FilmeAdapter(
    private val onClick: (String) -> Unit,
    private var items: List<Filme> = listOf()
) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    class FilmeViewHolder(val binding: FilmeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        return FilmeViewHolder(
            FilmeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val orientation = holder.itemView.context.resources.configuration.orientation
        holder.itemView.setOnClickListener { onClick(items[position].uuid) }
        holder.binding.nomeFilmeEditText.text = items[position].title
        holder.binding.cinemaEditText.text = items[position].userCinema
        holder.binding.avaliacaoValor.text = items[position].userRating
        holder.binding.dataEditText.text = items[position].userDate
        holder.binding.observacoesEditText.text = items[position].userObservations
        holder.binding.cinemaEditText.visibility =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE
        holder.binding.avaliacaoValor.visibility =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE
        holder.binding.observacoesEditText.visibility =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Filme>) {
        items = newItems
        notifyDataSetChanged()
    }
}

