package com.example.projetofinalpart1

import android.annotation.SuppressLint
import android.content.Intent
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
        binding.toolbar.title = getString(R.string.dashboard)
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
            R.id.bottom_paraVer ->
                NavigationManager.goToParaVerFragment(
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
            R.drawable.redemption,
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
            R.drawable.god_father,
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
            R.drawable.dark_nigth,
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
            R.drawable.pulp_fiction_cover,
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
            R.drawable.the_silence,
            "Crime, Drama, Thriller",
            "Uma agente do FBI tenta capturar um serial killer com a ajuda de um psicopata preso",
            "14/02/1991",
            "8.6",
            "https://www.imdb.com/title/tt0102926/",
            false
        )

        val filme6 = Filme(
            "Inception",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.inception,
            "Action, Adventure, Sci-Fi",
            "A skilled thief is hired to steal corporate secrets through use of dream-sharing technology",
            "16/07/2010",
            "8.8",
            "https://www.imdb.com/title/tt1375666/",
            false
        )

        val filme7 = Filme(
            "Interstellar",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.interstellar,
            "Adventure, Drama, Sci-Fi",
            "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival",
            "07/11/2014",
            "8.6",
            "https://www.imdb.com/title/tt0816692/",
            false
        )

        val filme8 = Filme(
            "The Matrix",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.matrix,
            "Action, Sci-Fi",
            "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers",
            "31/03/1999",
            "8.7",
            "https://www.imdb.com/title/tt0133093/",
            false
        )

        val filme9 = Filme(
            "The Prestige",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.prestige,
            "Drama, Mystery, Sci-Fi",
            "Two stage magicians engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other",
            "20/10/2006",
            "8.5",
            "https://www.imdb.com/title/tt0482571/",
            false
        )

        val filme10 = Filme(
            "Blade Runner",
            "",
            "",
            "",
            "",
            arrayListOf(),
            R.drawable.blade_runner,
            "Action, Sci-Fi, Thriller",
            "A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator",
            "25/06/1982",
            "9.9",
            "https://www.imdb.com/title/tt0083658/",
            false
        )
        listaTodosFilmes.add(filme1)
        listaTodosFilmes.add(filme2)
        listaTodosFilmes.add(filme3)
        listaTodosFilmes.add(filme4)
        listaTodosFilmes.add(filme5)
        listaTodosFilmes.add(filme6)
        listaTodosFilmes.add(filme7)
        listaTodosFilmes.add(filme8)
        listaTodosFilmes.add(filme9)
        listaTodosFilmes.add(filme10)
    }
}