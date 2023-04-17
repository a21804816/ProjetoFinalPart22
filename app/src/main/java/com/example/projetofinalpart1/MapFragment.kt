import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.example.projetofinalpart1.databinding.FragmentMapBinding
import com.google.android.material.tabs.TabLayout

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_map, container, false
        )
        binding = FragmentMapBinding.bind(view)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lista"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Mapa"))


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        NavigationManager.goToListFragment(requireFragmentManager())
                    }
                    1 -> {

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }




}
