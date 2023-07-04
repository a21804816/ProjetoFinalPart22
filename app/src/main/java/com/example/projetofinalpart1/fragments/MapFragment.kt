package com.example.projetofinalpart1.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.data.FilmeRepository
import com.example.projetofinalpart1.data.FilmeRoom
import com.example.projetofinalpart1.data.FilmsDatabase
import com.example.projetofinalpart1.databinding.FragmentMapBinding
import com.example.projetofinalpart1.model.Filme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private var map: GoogleMap? = null
    val repository = FilmeRepository.getInstance()
    private lateinit var objetoFilme: FilmeRoom
    private val filmList = mutableListOf<Filme>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        objetoFilme = FilmeRoom(FilmsDatabase.getInstance(requireContext()).filmDao())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding = FragmentMapBinding.bind(view)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { googleMap ->
            map = googleMap


            CoroutineScope(Dispatchers.IO).launch {
                repository.getFilmList { result ->
                    if (result.isSuccess) {
                        CoroutineScope(Dispatchers.Main).launch {
                            filmList.addAll(result.getOrDefault(emptyList()))
                            addMarkersOnMap()
                        }
                    }
                }
            }
            map?.setOnInfoWindowClickListener { clickedMarker ->
                val clickedFilme = clickedMarker.tag as? Filme
                clickedFilme?.let {
                    NavigationManager.goToDetalhesFragment(parentFragmentManager, clickedFilme.imdbID, "Mapa")
                }
            }

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map?.isMyLocationEnabled = true
            }
        }
    }

    fun addMarkersOnMap() {
        val cinemaNames = mutableListOf<String>()
        for (filme in filmList) {
            val cinemaName = filme.userCinema
            if (!cinemaNames.contains(cinemaName)) {  // && filme.genre.contains("Action")
                cinemaNames.add(cinemaName)
                val (existe, cinema) = objetoFilme.verificarCinemaExiste(cinemaName, context)
                if (existe && cinema != null) {
                    val cinemaLocation = LatLng(cinema.latitude, cinema.longitude)
                    val markerOptions = MarkerOptions().position(cinemaLocation).title(filme.title + " " + filme.userRating).icon(getMarkerIcon(filme.userRating.toInt()))
                    map?.addMarker(markerOptions)?.apply {
                        tag = filme
                    }
                }
            }
        }
    }

    private fun getMarkerIcon(userRating: Int): BitmapDescriptor {
        return when {
            userRating >= 10 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            userRating >= 8 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
            userRating >= 6 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
            userRating >= 4 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)
            userRating >= 2 -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            else -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    }
    private fun zoomToCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latLng = LatLng(location.latitude, location.longitude)
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }
        }
    }

}


