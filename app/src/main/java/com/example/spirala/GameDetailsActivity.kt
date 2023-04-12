package com.example.spirala

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.GameData.Companion.getDetails

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var game: Game
    private lateinit var title:TextView
    private lateinit var cover : ImageView
    private lateinit var platform : TextView
    private lateinit var release_date : TextView
    private lateinit var esrb : TextView
    private lateinit var developer : TextView
    private lateinit var publisher : TextView
    private lateinit var genre : TextView
    private lateinit var description : TextView
    private lateinit var homeBtn : Button
    private lateinit var detailsBtn : Button
    private lateinit var reviewList : RecyclerView
    private lateinit var reviewAdapter : ReviewListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        title= findViewById(R.id.game_title_textview)
        cover = findViewById(R.id.cover_imageview)
        platform=findViewById(R.id.platform_textview)
        release_date=findViewById(R.id.release_date_textview)
        esrb=findViewById(R.id.esrb_rating_textview)
        developer=findViewById(R.id.developer_textview)
        publisher=findViewById(R.id.publisher_textview)
        genre=findViewById(R.id.genre_textview)
        description=findViewById(R.id.description_textview)
        reviewList=findViewById(R.id.review_list)
        reviewAdapter = ReviewListAdapter(mutableListOf())
        val list : List<UserImpression> = getDetails(title.text as String)!!.userImpressions.sortedByDescending {  userImpression -> userImpression.timestamp }
        reviewAdapter.setReviews(list)
        reviewList.adapter=reviewAdapter
        reviewList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
    private fun fillDetails(){
        title.text=game.title
        platform.text=game.platform
        release_date.text=game.releaseDate
        esrb.text=game.esrbRating
        developer.text=game.developer
        publisher.text=game.publisher
        genre.text=game.genre
        description.text=game.description
        val id : Int = cover.context.resources.getIdentifier(
            game.coverImage,"drawable",cover.context.packageName)
        cover.setImageResource(id)
    }

}