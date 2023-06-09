package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException


object AccountGamesRepository {
        var acHash: String? = null
        var age: Int? = null
        lateinit var favoriteGames : MutableList<Game>
    fun setHash(acHash:String):Boolean{
        this.acHash=acHash
        return true
    }
    fun getHash() : String?{
        return acHash
    }
    fun setAge(age : Int) : Boolean{
        this.age = age
        return age in 3..100
    }
    fun getUserAge() : Int?{
        return this.age
    }
    fun setGames(games : List<Game>){
        this.favoriteGames= games as MutableList<Game>
    }
    fun getGames() : MutableList<Game>{
        return favoriteGames
    }
    suspend fun saveGame(game : Game) : Game{
        return withContext(Dispatchers.IO){
            val jsonObject = JSONObject()
            val gameJsonObject = JSONObject()
            gameJsonObject.put("igdb_id",game.id)
            gameJsonObject.put("name",game.title)
            jsonObject.put("game",gameJsonObject)
            print(jsonObject.toString())
            val mediaType = "application/json".toMediaTypeOrNull()
            AccountAPIConfig.retrofit.saveGame(game = jsonObject.toString().toRequestBody(mediaType))
            return@withContext game
        }
    }
    suspend fun removeGame(id: Int):Boolean {
        return withContext(Dispatchers.IO){
            val games = getSavedGames()
            AccountAPIConfig.retrofit.removeGame(id)
            for(game in games){
                if(game.id == id) return@withContext true
            }
            return@withContext false
        }
    }
    suspend fun getSavedGames() : List<Game>{
        return withContext(Dispatchers.IO){
            val savedGames = AccountAPIConfig.retrofit.getSavedGames().body()
            var games = mutableListOf<Game>()
            if(savedGames == null)
                return@withContext listOf()
            else {
                for (element in savedGames) {
                    games.add(getGameById(element.id)!!)
                }
            }
            return@withContext games
        }
    }

    suspend fun getGamesContainingString(query: String) : List<Game>{
        return withContext(Dispatchers.IO){
            val savedGames = AccountAPIConfig.retrofit.getSavedGames().body()
            val games = mutableListOf<Game>()
            if(savedGames == null)
                return@withContext listOf()
            else {
                for (element in savedGames) {
                    if(element.title.contains(query, ignoreCase = true)){
                        games.add(getGameById(element.id)!!)
                    }
                }
            }
            return@withContext games
        }
    }
    suspend fun removeNonSafe() : Boolean {
        return withContext(Dispatchers.IO){
            val games = getSavedGames()
            if(games.isEmpty()) return@withContext false
            var br = 0
            for(i in games.indices){
                if(games[i].esrbRating == "AO" || games[i].esrbRating == "M"){
                    val deleted = removeGame(games[i].id)
                }
            }
            setGames(games)
            return@withContext true
        }
    }
    suspend fun isGameSaved(id:Int):Boolean{
        val games = getSavedGames()
        for(game in games)
            if(game.id==id) return true
        return false
    }


}