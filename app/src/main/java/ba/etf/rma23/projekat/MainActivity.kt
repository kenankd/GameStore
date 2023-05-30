package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setHash
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGamesByName
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGamesSafe
import com.example.spirala.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment).navController.navigate(
                HomeFragmentDirections.toDetails2(getAll()[0].title))
            if((supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment).childFragmentManager.fragments.size != 0) {
                if ((supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment).childFragmentManager.fragments[0] is GameDetailsFragment) {
                    (supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment).navController.navigate(
                        GameDetailsFragmentDirections.toHome())
                }
            }
        }
        else{
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
        }
        setHash("417fe823-f22d-41a3-b7bb-14e4d5fcfd83")
        CoroutineScope(Job() + Dispatchers.Main).launch{
            val games = getGamesSafe("Hitman")
            val a = games?.size
        }


    }

}


