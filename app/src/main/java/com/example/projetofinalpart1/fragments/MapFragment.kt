package com.example.projetofinalpart1.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R


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


}
