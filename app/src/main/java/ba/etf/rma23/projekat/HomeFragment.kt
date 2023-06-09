package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.GameDetailsFragmentDirections
import ba.etf.rma23.projekat.HomeFragmentArgs
import ba.etf.rma23.projekat.HomeFragmentDirections
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getGamesContainingString
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getSavedGames

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getUserAge
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGamesByName
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGamesSafe
import ba.etf.rma23.projekat.data.repositories.GamesRepository.sortGames
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.*

class HomeFragment: Fragment() {
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private lateinit var searchButton : Button
    private lateinit var searchText : EditText
    private lateinit var sortSwitch : Switch
    private lateinit var favoritesSwitch : Switch
    private val args : HomeFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homefragment,container,false)
        gameList = view.findViewById(R.id.game_list)
        gameList.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        searchButton=view.findViewById(R.id.search_button)
        searchText=view.findViewById(R.id.search_query_edittext)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            sortSwitch=view.findViewById(R.id.sortSwitch)
            favoritesSwitch=view.findViewById(R.id.favoritesSwitch)
            val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            bottomNav.menu.getItem(0).isEnabled = false
            bottomNav.menu.getItem(1).isEnabled = arguments?.containsKey("game") == true
            bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.gameDetailsItem -> {
                        val bundle = Bundle().apply {
                            putString("game", arguments?.getString("game"))
                        }
                        requireView().findNavController().navigate(R.id.gameDetailsItem,bundle)
                        true
                    }
                    else -> true
                }
            }
            gameListAdapter = GameListAdapter(listOf()) { game -> showGame(game) }
            setSavedGames()
            searchButton.setOnClickListener {
                if(getUserAge()!=null){
                    CoroutineScope(Job() + Dispatchers.Main).launch{
                        if(favoritesSwitch.isChecked){
                            gameListAdapter.setGames(getGamesContainingString(searchText.text.toString()))
                            return@launch
                        }
                        val games : List<Game> = if(getUserAge()!! >= 18)
                            getGamesByName(searchText.text.toString())!!
                        else
                            getGamesSafe(searchText.text.toString())!!
                        if(sortSwitch.isChecked)
                            gameListAdapter.setGames(sortGames())
                        else gameListAdapter.setGames(games)
                    }
                }
            }
        }
        else{
            gameListAdapter = GameListAdapter(listOf()){game -> showGameLand(game) }
            setSavedGames()
            searchButton.setOnClickListener {
                if(getUserAge()!=null){
                    CoroutineScope(Job() + Dispatchers.Main).launch{
                        if(getUserAge()!! >= 18)
                            gameListAdapter.setGames(getGamesByName(searchText.text.toString())!!)
                        else
                            gameListAdapter.setGames(getGamesSafe(searchText.text.toString())!!)
                    }
                }
            }
        }

        gameList.adapter=gameListAdapter
        gameList.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun showGame(game:Game){
        val bundle = Bundle().apply {
            putString("game", Gson().toJson(game))
        }
        requireView().findNavController().navigate(R.id.gameDetailsItem,bundle)
    }
    private fun showGameLand(game:Game){
        val bundle = Bundle().apply {
            putString("game", Gson().toJson(game))
        }
        (requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment).navController.navigate(
            R.id.gameDetailsItem,bundle)
    }
    private fun setSavedGames(){
        CoroutineScope(Job() + Dispatchers.Main).launch {
            gameListAdapter.setGames(getSavedGames())
        }
    }

}