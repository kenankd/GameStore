package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.spirala.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment: Fragment() {
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private val args : HomeFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homefragment,container,false)
        gameList = view.findViewById(R.id.game_list)
        gameList.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav)
            bottomNav.menu.getItem(0).isEnabled = false
            bottomNav.menu.getItem(1).isEnabled = args.title != null
            bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.gameDetailsItem -> {
                        findNavController().navigate(HomeFragmentDirections.toDetails2(args.title!!))
                        true
                    }
                    else -> true
                }
            }
            gameListAdapter = GameListAdapter(getAll()){game -> showGame(game) }
        }
        else{
            gameListAdapter = GameListAdapter(getAll()){game -> showGameLand(game) }
        }
        gameList.adapter=gameListAdapter
        gameList.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun showGame(game:Game){
        requireView().findNavController().navigate(HomeFragmentDirections.toDetails2(game.title))
    }
    private fun showGameLand(game:Game){
        (requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment).navController.navigate(
            GameDetailsFragmentDirections.toDetails2(game.title))
    }

}