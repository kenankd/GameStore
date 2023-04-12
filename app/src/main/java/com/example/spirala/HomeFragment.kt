package com.example.spirala

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.GameData.Companion.getAll
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment: Fragment() {
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private val args : HomeFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homefragment,container,false)
        gameList = view.findViewById(R.id.game_list)
        gameListAdapter = GameListAdapter(getAll()){game -> showGame(game) }
        gameList.adapter=gameListAdapter
        gameList.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        var bottomNav : BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.menu.getItem(0).isEnabled = !args.title.equals("nogame")
        bottomNav.menu.getItem(1).isEnabled = !args.title.equals("nogame")
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.gameDetailsFragment -> {
                    findNavController().navigate(HomeFragmentDirections.toDetails(args.title))
                    true
                }
                else -> true
            } }
        return view
    }
    private fun showGame(game:Game){
        val action = HomeFragmentDirections.toDetails(game.title)
        requireView().findNavController().navigate(action)
    }
}