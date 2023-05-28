package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException


object AccountGamesRepository {
        var acHash: String? = null
        var age: Int? = null
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
            if(savedGames != null)
                return@withContext listOf()
            var games = mutableListOf<Game>()
            //zavrsiti treba pozvati getGamesByName ili napraviti metodu getGameById i zavrsiti
            return@withContext listOf()
        }

    }


}