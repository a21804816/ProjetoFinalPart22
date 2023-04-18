import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentDetalhesBinding
import com.example.projetofinalpart1.listaFilmes

class DetalhesFragment : Fragment() {

    private lateinit var binding: FragmentDetalhesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetalhesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nomeFilme = arguments?.getString("nomeFilme")
        val nomeCinema = arguments?.getString("nomeCinema")
        val avaliacao = arguments?.getString("avaliacao")
        val dataVisualizacao = arguments?.getString("dataVisualizacao")
        val observacoes = arguments?.getString("observacoes")


        binding.nomeFilmeTextView.text = nomeFilme
        binding.nomeCinemaTextView.text = nomeCinema
        binding.avaliacaoTextView.text = avaliacao
        binding.dataVisualizacaoTextView.text = dataVisualizacao
        binding.observacoesTextView.text = observacoes

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (binding.voltarButton).setOnClickListener {
            requireActivity().onBackPressed()
        }


    }
}

