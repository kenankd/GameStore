package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.favoriteGames
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getSavedGames
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.removeGame
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setAge
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setHash
import ba.etf.rma23.projekat.data.repositories.AppDatabase
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.getOfflineReviews
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.sendReview
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import ba.etf.rma23.projekat.data.repositories.GamesRepository.savedGames
import com.example.spirala.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
        }
        setHash("417fe823-f22d-41a3-b7bb-14e4d5fcfd83")
        setAge(19)
        runBlocking{
            favoriteGames=getSavedGames() as MutableList<Game>
        }
    }
}


