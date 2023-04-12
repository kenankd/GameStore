package com.example.spirala

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageAndVideo.equals
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.GameData.Companion.getAll
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class HomeFragment: Fragment() {
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    //val args : HomeFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homefragment,container,false)
        gameList = view.findViewById(R.id.game_list)
        gameListAdapter = GameListAdapter(getAll()){game -> showGame(game) }
        gameList.adapter=gameListAdapter
        gameList.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun showGame(game:Game){
        val action = HomeFragmentDirections.toDetails()
        requireView().findNavController().navigate(action)
    }
}