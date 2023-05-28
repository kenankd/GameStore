package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GamesRepository {
    suspend fun getToken(client_id : String, client_secret : String, grant_type : String) : TokenData?{
        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getToken(client_id,client_secret,grant_type)
            return@withContext response.body()
        }
    }
    suspend fun getGamesByName(name: String) : List<Game>?{
        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getGamesByName(
                "dfui4ski9ctfq9pxvaktgsstb61fdz",
                "Bearer 1nnymjagwtq9mum6481xr13g1g1pi6", name= name)
            return@withContext response.body()
        }
    }

}