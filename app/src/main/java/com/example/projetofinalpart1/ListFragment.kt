package com.example.projetofinalpart1

import FilmeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayout

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_list, container, false
        )
        binding = FragmentListBinding.bind(view)

        val adapter = FilmeAdapter(listaFilmes) { filme ->

            val bundle = Bundle().apply {
                putString("nomeFilme", filme.nomeFilme)
                putString("nomeCinema", filme.nomeCinema)
                putString("avaliacao", filme.avaliacao)
                putString("dataVisualizacao", filme.dataVisualizacao)
                putString("observacoes", filme.observacoes)
            }

            NavigationManager.goToDetalhesFragment(requireActivity().supportFragmentManager, bundle)
        }

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lista"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Mapa"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {

                    }
                    1 -> {
                        NavigationManager.goToMapFragment(requireActivity().supportFragmentManager)
                        binding.tabLayout.getTabAt(1)?.select()
                    }
                }
                binding.tabLayout.getTabAt(tab?.position ?: 0)?.select()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        return binding.root
    }
}



