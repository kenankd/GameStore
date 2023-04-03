package com.example.spirala

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.GameData.Companion.getDetails

class HomeActivity : AppCompatActivity() {
    private lateinit var homeBtn: Button
    private lateinit var detailsBtn : Button
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        homeBtn= findViewById(R.id.home_button)
        homeBtn.isEnabled=false
        detailsBtn= findViewById(R.id.details_button)
        gameList = findViewById(R.id.game_list)
        gameListAdapter = GameListAdapter(listOf()){game -> showGame(game)}
        gameListAdapter.setGames(GameData.getAll())
        gameList.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        gameList.adapter=gameListAdapter
        var extras = intent.extras
        if(extras==null){
            detailsBtn.isEnabled=false
        }
        else{
            detailsBtn.setOnClickListener{
                showGame(getDetails(extras.getString("game_title",""))!!)
            }
        }
    }
    private fun showGame(game:Game){
        var intent : Intent = Intent(this,GameDetailsActivity::class.java).apply{
            putExtra("game_title",game.title)
        }
        try{
            startActivity(intent)
        }
        catch(e:ActivityNotFoundException){
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }
    }
}


