package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getHash
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import org.json.JSONObject
import retrofit2.http.*

interface AccountApi {

    @POST("account/{aid}/game")
    suspend fun saveGame(@Path("aid") aid: String = getHash()!!, @Body game: RequestBody)

    @DELETE("account/{aid}/game/{gid}/")
    suspend fun removeGame(@Path("gid") gid : Int,@Path("aid") aid: String = getHash()!!)

    @GET("account/{aid}/games")
    suspend fun getSavedGames(@Path("aid") aid: String = getHash()!!) : Response<List<SavedGame>>

    @POST("account/{aid}/game/{gid}/gamereview")
    suspend fun sendReview(@Path("aid") aid: String = getHash()!!,@Path("gid") gid: Int, @Body gameReview: RequestBody) : Response<ResponseBody>

    @GET("game/{gid}/gamereviews")
    suspend fun getReviewsForGame(@Path("gid") gid: Int) : Response<List<GameReview>>
}