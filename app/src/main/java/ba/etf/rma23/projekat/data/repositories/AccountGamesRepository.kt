package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException


object AccountGamesRepository {
        var acHash: String? = null
        var age: Int? = null
        lateinit var favoriteGames : List<Game>
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
    fun setGames(games : List<Game>){
        this.favoriteGames=games
    }
    fun getGames() : List<Game>{
        return favoriteGames
    }
    suspend fun saveGame(game : Game) : Game{
        return withContext(Dispatchers.IO){
            val jsonObject = JSONObject()
            val gameJsonObject = JSONObject()
            gameJsonObject.put("igdb_id",game.id)
            gameJsonObject.put("name",game.title)
            jsonObject.put("game",gameJsonObject)
            val mediaType = "application/json".toMediaTypeOrNull()
            AccountAPIConfig.retrofit.saveGame(game = jsonObject.toString().toRequestBody(mediaType))
            return@withContext game
        }
    }
    suspend fun removeGame(id: Int):Boolean {
        return withContext(Dispatchers.IO){
            //treba mijenjati try catch ne baca nikad izuzetak
            try{
                AccountAPIConfig.retrofit.removeGame(id)
            }
            catch(e : Exception){
                return@withContext false
            }
            return@withContext true
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
            var games = mutableListOf<Game>()
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

        }
    }


}