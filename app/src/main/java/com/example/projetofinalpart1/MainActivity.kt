package com.example.projetofinalpart1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater.*
import android.view.MenuItem
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.projetofinalpart1.databinding.ActivityMainBinding
import com.example.projetofinalpart1.model.Filme

val listaTodosFilmes = ArrayList<Filme>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!screenRotated(savedInstanceState)) {
            NavigationManager.goToDashboardFragment(supportFragmentManager)
        }
        binding.toolbar.title = "Páginanicial"
        introduzirFilmes()
        NavigationManager.goToDashboardFragment(
            supportFragmentManager
        )
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
            R.id.nav_map ->
                NavigationManager.goToMapFragment(
                    supportFragmentManager
                )
            R.id.para_ver ->
                NavigationManager.goToParaVerFragment(
                    supportFragmentManager
                )
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onClickNavigationItemBottom(item: MenuItem): Boolean {
        when (item.itemId) {
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
            R.id.bottom_map ->
                NavigationManager.goToMapFragment(
                    supportFragmentManager
                )
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }

    @SuppressLint("InflateParams")
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

    private fun introduzirFilmes() {
        val filme1 = Filme(
            "The Shawshank Redemption",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.mapa,
            "Drama",
            "Dois homens presos em uma prisão em 1940s se tornam amigos",
            "23/09/1994",
            "9.3",
            "https://www.imdb.com/title/tt0111161/",
            false
        )

        val filme2 = Filme(
            "The Godfather",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.mapa,
            "Crime, Drama",
            "Um mafioso tenta proteger sua família enquanto enfrenta rivais",
            "24/03/1972",
            "9.2",
            "https://www.imdb.com/title/tt0068646/",
            false
        )

        val filme3 = Filme(
            "The Dark Knight",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.mapa,
            "Action, Crime, Drama",
            "Batman enfrenta seu maior desafio quando o Coringa tenta destruir Gotham City",
            "18/07/2008",
            "9.0",
            "https://www.imdb.com/title/tt0468569/",
            false
        )

        val filme4 = Filme(
            "Pulp Fiction",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.mapa,
            "Crime, Drama",
            "Várias histórias se entrelaçam em Los Angeles",
            "14/10/1994",
            "8.9",
            "https://www.imdb.com/title/tt0110912/",
            false
        )

        val filme5 = Filme(
            "The Silence of the Lambs",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.mapa,
            "Crime, Drama, Thriller",
            "Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso",
            "14/02/1991",
            "8.6",
            "https://www.imdb.com/title/tt0102926/",
            false
        )

        val filme6 = Filme(
            "Q",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.mapa,
            "Crime, Drama, Thriller",
            "Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso, Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso,",
            "14/02/1991",
            "8.6",
            "https://www.imdb.com/title/tt0102926/",
            false
        )
        listaTodosFilmes.add(filme4)
        listaTodosFilmes.add(filme2)
        listaTodosFilmes.add(filme3)
        listaTodosFilmes.add(filme1)
        listaTodosFilmes.add(filme5)
        listaTodosFilmes.add(filme6)
    }


}