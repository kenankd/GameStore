package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setAge
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setHash
import ba.etf.rma23.projekat.data.repositories.AppDatabase
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository.sendReview
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import com.example.spirala.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
        else{
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
        }
        setHash("417fe823-f22d-41a3-b7bb-14e4d5fcfd83")
        setAge(19)
        /*
        val context = this
        CoroutineScope(Job()+ Dispatchers.Main).launch{
            val gameReview = GameReview(1,null,"Great game!",83517,true)
            sendReview(gameReview,context)
        }
        */


    }

}


