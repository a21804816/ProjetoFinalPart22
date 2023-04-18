import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.util.*
import com.example.projetofinalpart1.databinding.MovieItemBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_dashboard, container, false
        )
        binding = FragmentDashboardBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set user name and image
        binding.userName.text = "Hi, John"
        binding.userImage.setImageResource(R.drawable.movie1)

        // Set up movies list
        val movies = listOf(
            R.drawable.movie1,
            R.drawable.movie2,
            R.drawable.movie3
        )

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesList.layoutManager = layoutManager
        binding.moviesList.adapter = MoviesAdapter(movies)
    }

}
class MoviesAdapter(val movies: List<Int>): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private lateinit var binding: MovieItemBinding

    inner class MovieViewHolder(binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        val movieImage: ImageView = binding.movieImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieImage.setImageResource(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}
data class Movie(val imageResourceId: Int)

