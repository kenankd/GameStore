package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.IGDBApi
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig
import ba.etf.rma23.projekat.data.repositories.TokenData
import ba.etf.rma23.projekat.GameDetailsFragmentDirections
import ba.etf.rma23.projekat.HomeFragmentDirections
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.removeGame
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.saveGame
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setHash
import com.example.spirala.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class HomeActivity : AppCompatActivity() {
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
            setHash("417fe823-f22d-41a3-b7bb-14e4d5fcfd83")
            val a = removeGame(53535)
            Log.d("TAG",a.toString())
        }


    }

}


