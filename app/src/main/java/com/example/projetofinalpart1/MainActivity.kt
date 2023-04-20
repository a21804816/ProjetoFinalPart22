package com.example.projetofinalpart1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.MenuItem
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.projetofinalpart1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)
        }
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        binding.btnToolbar.setOnClickListener {
            showPopup()
        }
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener{
            onClickNavigationItem(it)
            true
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            onClickNavigationItemBottom(it)
            true
        }

        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_dashboard ->

                NavigationManager.goToDashboardFragment(
                    supportFragmentManager
                )
            R.id.nav_regist ->
                NavigationManager.goToRegistFragment(
                    supportFragmentManager
                )
            R.id.nav_list ->
                NavigationManager.goToListFragment(
                    supportFragmentManager
                )
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onClickNavigationItemBottom(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.bottom_dashboard ->
                NavigationManager.goToDashboardFragment(
                    supportFragmentManager
                )
            R.id.bottom_regist ->
                NavigationManager.goToRegistFragment(
                    supportFragmentManager
                )
            R.id.bottom_list ->
                NavigationManager.goToListFragment(
                    supportFragmentManager
                )
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }
    private fun showPopup() {
        val inflater = from(this)
        val popupView = inflater.inflate(R.layout.popup_layout, null)

        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.elevation = 10f
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        var count = 9
        val countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                popupView.findViewById<TextView>(R.id.popup_countdown).text = count.toString()
                count--
            }

            override fun onFinish() {
                popupWindow.dismiss()
            }
        }
        countDownTimer.start()
    }




}