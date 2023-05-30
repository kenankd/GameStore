package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getGames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object GamesRepository {
    suspend fun getToken(client_id : String, client_secret : String, grant_type : String) : TokenData?{
        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getToken(client_id,client_secret,grant_type)
            return@withContext response.body()
        }
    }
    suspend fun getGamesByName(name: String) : List<Game>?{
        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getGamesByName(name= name)
            return@withContext response.body()
        }
    }
    suspend fun getGamesSafe(name: String) : List<Game>?{
        return withContext(Dispatchers.IO){
            val body = "where age_ratings.category = 1 &  age_ratings.rating!=(11,12); search \"Hitman\";"
            val response = IGDBApiConfig.retrofit.getGamesSafe( body = body.toRequestBody("text/plain".toMediaTypeOrNull())
            )
            return@withContext response.body()
        }
    }
    fun sortGames():List<Game>{
        val games = getGames()
        return games
    }
    suspend fun getGameById(id : Long) : Game?{
        return withContext(Dispatchers.IO){
            val body = "fields id,name,platforms.name,genres.name,involved_companies.company.name,age_ratings.category,age_ratings.rating,release_dates.y,cover.url,summary;" +
                    " where id = " + id + ";"
            val response = IGDBApiConfig.retrofit.getGameById("dfui4ski9ctfq9pxvaktgsstb61fdz",
            "Bearer 1nnymjagwtq9mum6481xr13g1g1pi6",body.toRequestBody("text/plain".toMediaTypeOrNull())
                )
            return@withContext response.body()
        }
    }

}