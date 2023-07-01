package com.example.projetofinalpart1.fragments

import FilmeAdapter
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinalpart1.FusedLocation
import com.example.projetofinalpart1.FusedLocation.Companion.notifyListeners
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.data.FilmeRepository
import com.example.projetofinalpart1.data.FilmeRoom
import com.example.projetofinalpart1.data.FilmsDatabase
import com.example.projetofinalpart1.databinding.DialogLayoutBinding
import com.example.projetofinalpart1.databinding.FragmentListBinding
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.listaFilmesVistos
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.*

class ListFragment : Fragment(), FusedLocation.OnLocationChangedListener {
    private val adapter = FilmeAdapter(::onOperationClick, listaFilmesVistos)
    private lateinit var binding: FragmentListBinding
    private val filmList = mutableListOf<Filme>()
    private val filteredFilmList = mutableListOf<Filme>()
    private lateinit var filterButton: Button
    private lateinit var objetoFilme: FilmeRoom

    private val REQUEST_CODE_SPEECH_INPUT = 100
    val repository = FilmeRepository.getInstance()

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0
    private var distanceFilter: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        objetoFilme = FilmeRoom(FilmsDatabase.getInstance(requireContext()).filmDao())
        filterButton = binding.filterButton!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), filterButton)
            popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item -> handleFilterMenuItem(item) }
            popupMenu.show()
        }

        FusedLocation.start(requireContext())
    }

    private fun handleFilterMenuItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_500m -> {
                filterByDistance(500.0)
                return true
            }

            R.id.filter_1000m -> {
                filterByDistance(15000.0)
                return true
            }

            R.id.filter_reset -> {
                resetFilter()
                return true
            }

            R.id.filter_crescente -> {
                filterByRatingAscending()
                return true
            }

            R.id.filter_decrescente -> {
                filterByRatingDescending()
                return true
            }

            else -> return false
        }
    }

    override fun onStart() {
        super.onStart()
        FusedLocation.registerListener(this)

        val (latitude, longitude) = FusedLocation.getCurrentLocation()
        currentLatitude = latitude
        currentLongitude = longitude

        Log.i("APP", currentLatitude.toString())
        Log.i("APP", currentLongitude.toString())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        resetFilter()

        binding.textEmptyList!!.text = filteredFilmList.size.toString()

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredFilmList.clear()
                if (!newText.isNullOrEmpty()) {
                    filteredFilmList.addAll(filmList.filter { filme ->
                        filme.title.contains(newText, ignoreCase = true)
                    })
                } else {
                    filteredFilmList.addAll(filmList)
                }
                adapter.setData(filteredFilmList)
                return true
            }
        })

        binding.fabMicrophone.setOnClickListener {
            val dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(false)

            val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_INPUT)
        }
    }

    override fun onStop() {
        super.onStop()
        FusedLocation.unregisterListener()
    }

    private fun filterByDistance(distanceFilter: Double) {
        this.distanceFilter = distanceFilter

        updateFilteredFilmList(filmList)
    }

    private fun updateFilteredFilmList(films: List<Filme>) {
        filteredFilmList.clear()

        for (filme in films) {
            val (existe, cinema) = objetoFilme.verificarCinemaExiste(filme.userCinema, context)
            if (existe) {
                val cinemaLatitude = cinema?.latitude ?: continue
                val cinemaLongitude = cinema?.longitude ?: continue

                val distance = calculateDistance(
                    currentLatitude, currentLongitude, cinemaLatitude, cinemaLongitude
                )

                if (distance <= distanceFilter) {
                    filteredFilmList.add(filme)
                }
            }
        }

        adapter.setData(filteredFilmList)
    }

    private fun resetFilter() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getFilmList { result ->
                if (result.isSuccess) {
                    CoroutineScope(Dispatchers.Main).launch {
                        filmList.clear()
                        filmList.addAll(result.getOrDefault(emptyList()))
                        filteredFilmList.clear()
                        filteredFilmList.addAll(filmList)
                        adapter.setData(filteredFilmList)
                    }
                }
            }
        }
    }

    private fun filterByRatingAscending() {
        filteredFilmList.sortBy { it.userRating }
        adapter.setData(filteredFilmList)
    }

    private fun filterByRatingDescending() {
        filteredFilmList.sortByDescending { it.userRating }
        adapter.setData(filteredFilmList)
    }

    private fun onOperationClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.searchView.setQuery(result?.get(0), true)
        }
    }

    private fun calculateDistance(
        lat1: Double, lon1: Double, lat2: Double, lon2: Double
    ): Double {
        val earthRadius = 6371000.0 // Raio médio da Terra em metros

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = earthRadius * c

        return distance
    }

    override fun onLocationChanged(latitude: Double, longitude: Double) {
        currentLatitude = latitude
        currentLongitude = longitude

    }

    fun onLocationResult(locationResult: LocationResult) {
        Log.i(TAG, locationResult?.lastLocation.toString())
        notifyListeners(locationResult)

        // Atualizar os valores de currentLatitude e currentLongitude
        currentLatitude = locationResult.lastLocation!!.latitude
        currentLongitude = locationResult.lastLocation!!.longitude
    }
}
