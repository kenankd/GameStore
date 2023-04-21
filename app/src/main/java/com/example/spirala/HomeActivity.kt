package com.example.spirala

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.spirala.GameData.Companion.getAll
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment).navController.navigate(HomeFragmentDirections.toDetails(getAll()[0].title))
            if((supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment).childFragmentManager.fragments.size != 0) {
                if ((supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment).childFragmentManager.fragments[0] is GameDetailsFragment) {
                    (supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment).navController.navigate(GameDetailsFragmentDirections.actionGameDetailsFragmentToHomeFragment())
                }
            }
        }
        else{
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
        }

    }

}


