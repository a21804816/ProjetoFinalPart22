package com.example.projetofinalpart1

import DashboardFragment
import DetalhesFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object NavigationManager {
    private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToDashboardFragment(fm: FragmentManager) {
        placeFragment(fm, DashboardFragment())
    }

    fun goToRegistFragment(fm: FragmentManager) {
        placeFragment(fm, RegistFragment())
    }

    fun goToListFragment(fm: FragmentManager) {
        placeFragment(fm, ListFragment())
    }
    fun goToMapFragment(fm: FragmentManager) {
        placeFragment(fm, MapFragment())
    }
    fun goToDetalhesFragment(fragmentManager: FragmentManager, bundle: Bundle) {
        val detalhesFragment = DetalhesFragment()
        detalhesFragment.arguments = bundle
        placeFragment(fragmentManager, detalhesFragment)
    }




}