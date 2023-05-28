package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spirala.R

class GameListAdapter(private var games:List<Game>,private val GameClicked : (game : Game )-> Unit): RecyclerView.Adapter<GameListAdapter.GameHolder>(){
    inner class GameHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.item_title_textview)
        val rating : TextView = itemView.findViewById(R.id.game_rating_textview)
        val date : TextView = itemView.findViewById(R.id.game_release_date_textview)
        val platform : TextView = itemView.findViewById(R.id.game_platform_textview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game,parent,false)
        return GameHolder(view)
    }

    override fun getItemCount(): Int=games.size
    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.title.text=games[position].title
        holder.rating.text=games[position].rating.toString()
        holder.date.text=games[position].release_date
        holder.platform.text=games[position].platform
        holder.itemView.setOnClickListener{
            GameClicked(games[position])
        }
    }
    fun setGames(games : List<Game>){
        this.games=games
        notifyDataSetChanged()
    }
}