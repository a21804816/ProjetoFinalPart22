package com.example.projetofinalpart1

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.RecognizerIntent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import com.example.projetofinalpart1.data.FilmDao
import com.example.projetofinalpart1.data.FilmeRepository
import com.example.projetofinalpart1.data.FilmeRoom
import com.example.projetofinalpart1.data.FilmsDatabase
import com.example.projetofinalpart1.databinding.ActivityMainBinding
import com.example.projetofinalpart1.databinding.DialogLayoutBinding
import com.example.projetofinalpart1.model.Filme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val listaTodosFilmes = ArrayList<Filme>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var objetoFilme: FilmeRoom
    private val REQUEST_CODE_SPEECH_INPUT = 100
    private var recognizedText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)
        }
        binding.toolbar.title = getString(R.string.dashboard)
        NavigationManager.goToDashboardFragment(supportFragmentManager)
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        objetoFilme = FilmeRoom(FilmsDatabase.getInstance(this).filmDao())

        binding.fabMicrophone.setOnClickListener {
            val dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
            val dialog = Dialog(this)
            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(false)

            val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            dialogBinding.searchButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (recognizedText != null) {
                        dialog.dismiss()
                        val movieMatch = recognizedText?.let { objetoFilme.getFilmByTitle(it) }
                        if (movieMatch != null) {
                            NavigationManager.goToDetalhesFragment(supportFragmentManager, movieMatch.uuid)
                        } else {
                            Toast.makeText(this@MainActivity, "Movie not found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Please speak to search for a movie", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            dialogBinding.cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.retryButton.setOnClickListener {
                dialogBinding.promptTextView.text = ""
                recognizedText = null
            }

            startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_INPUT)
            dialogBinding.promptTextView.text = recognizedText

            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            recognizedText = result?.get(0)

        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener {
            onClickNavigationItem(it)
            binding.toolbar.title = it.title
            true
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            onClickNavigationItemBottom(it)
            binding.toolbar.title = it.title
            true
        }

        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> NavigationManager.goToDashboardFragment(supportFragmentManager)
            R.id.nav_regist -> NavigationManager.goToRegistFragment(supportFragmentManager)
            R.id.nav_list -> NavigationManager.goToListFragment(supportFragmentManager)
            R.id.nav_map -> NavigationManager.goToMapFragment(supportFragmentManager)
            R.id.para_ver -> NavigationManager.goToParaVerFragment(supportFragmentManager)
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onClickNavigationItemBottom(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_dashboard -> NavigationManager.goToDashboardFragment(supportFragmentManager)
            R.id.bottom_regist -> NavigationManager.goToRegistFragment(supportFragmentManager)
            R.id.bottom_list -> NavigationManager.goToListFragment(supportFragmentManager)
            R.id.bottom_map -> NavigationManager.goToMapFragment(supportFragmentManager)
            R.id.bottom_paraVer -> NavigationManager.goToParaVerFragment(supportFragmentManager)
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }
}
