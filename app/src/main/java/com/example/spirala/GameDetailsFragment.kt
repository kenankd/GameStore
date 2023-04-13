package com.example.spirala

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.GameData.Companion.getAll
import com.example.spirala.GameData.Companion.getDetails
import com.google.android.material.bottomnavigation.BottomNavigationView

class GameDetailsFragment : Fragment(){
    private lateinit var game: Game
    private lateinit var title: TextView
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
    val args: GameDetailsFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.gamedetailsfragment,container,false)
        title= view.findViewById(R.id.game_title_textview)
        cover = view.findViewById(R.id.cover_imageview)
        platform=view.findViewById(R.id.platform_textview)
        release_date=view.findViewById(R.id.release_date_textview)
        esrb=view.findViewById(R.id.esrb_rating_textview)
        developer=view.findViewById(R.id.developer_textview)
        publisher=view.findViewById(R.id.publisher_textview)
        genre=view.findViewById(R.id.genre_textview)
        description=view.findViewById(R.id.description_textview)
        reviewList=view.findViewById(R.id.review_list)
        reviewAdapter = ReviewListAdapter(mutableListOf())
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            game = if(arguments==null)
                getAll()[0]
            else getDetails(arguments!!.getString("title",""))!!
            fillDetails()
        }
        else {
            var bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            bottomNav.menu.getItem(0).isEnabled=true
            bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> {
                        findNavController().navigate(GameDetailsFragmentDirections.actionGameDetailsFragmentToHomeFragment(game.title))
                        true
                    }
                    else -> true
                } }
            game = getDetails(args.title)!!
            fillDetails()
        }
        val list : List<UserImpression> = GameData.getDetails(title.text as String)!!.userImpressions.sortedByDescending { userImpression -> userImpression.timestamp }
        reviewAdapter.setReviews(list)
        reviewList.adapter=reviewAdapter
        reviewList.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        return view
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