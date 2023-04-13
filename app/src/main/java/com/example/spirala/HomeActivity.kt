package com.example.spirala

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.GameData.Companion.getDetails
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
           /* val a = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            if((supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).childFragmentManager.fragments[0] is GameDetailsFragment){
                val homeFragment : HomeFragment = HomeFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView,homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }*/
        }
        else{
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
        }

    }

}


