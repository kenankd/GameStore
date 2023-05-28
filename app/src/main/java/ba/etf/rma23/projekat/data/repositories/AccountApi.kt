package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getHash
import okhttp3.RequestBody
import retrofit2.Response
import org.json.JSONObject
import retrofit2.http.*

interface AccountApi {

    @POST("{aid}/game")
    suspend fun saveGame(@Path("aid") aid: String = getHash()!!, @Body game: RequestBody)

    @DELETE("{aid}/game/{gid}/")
    suspend fun removeGame(@Path("gid") gid : Int,@Path("aid") aid: String = getHash()!!)

    @GET("{aid}/games")
    suspend fun getSavedGames(@Path("aid") aid: String = getHash()!!) : Response<List<SavedGame>>
}