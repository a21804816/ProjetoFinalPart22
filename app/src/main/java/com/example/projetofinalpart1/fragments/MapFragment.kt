package com.example.projetofinalpart1.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projetofinalpart1.R
import com.example.projetofinalpart1.data.FilmeRepository
import com.example.projetofinalpart1.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private var map: GoogleMap? = null
    val repository = FilmeRepository.getInstance()


    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { googleMap ->
            map = googleMap

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                map?.isMyLocationEnabled = true
                map?.addMarker(
                    MarkerOptions()
                        .position(LatLng(38.75814, -9.15179))
                        .title("ULHT 1")
                )
                addMarkerOnMap(39.75815, -9.15178, "Cinema do Parque")

            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map?.isMyLocationEnabled = true
                addMarkerOnMap(38.75814, -9.15179, "ULHT")
            }
        }
    }

    fun addMarkerOnMap(latitude: Double, longitude: Double, nomeCinema: String) {
        val cinemaLocation = LatLng(latitude, longitude)
        val markerOptions = MarkerOptions().position(cinemaLocation).title(nomeCinema)
        map?.addMarker(markerOptions)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(cinemaLocation, 0f))
    }


}

