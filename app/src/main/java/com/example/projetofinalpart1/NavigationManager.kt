package com.example.projetofinalpart1

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

}