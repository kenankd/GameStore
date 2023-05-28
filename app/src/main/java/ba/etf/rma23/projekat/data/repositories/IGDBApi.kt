package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import retrofit2.Response
import retrofit2.http.*

interface IGDBApi {
    @POST("https://id.twitch.tv/oauth2/token")
    suspend fun getToken(@Query("client_id") client_id : String,@Query("client_secret") client_secret : String,@Query("grant_type") grant_type : String)
    : Response<TokenData>
    @POST("games")
    suspend fun getGamesByName(@Header("Client-ID") client_id : String,
                               @Header("Authorization") authorization : String,
                               @Query("fields") fields: String = "id,name,platforms.name,genres.name,involved_companies.company.name,age_ratings.category,age_ratings.rating,release_dates.y,cover.url,summary",
                               @Query("search") name : String): Response<List<Game>>
    @POST("games")
    suspend fun getGamesSafe(@Header("Client-ID") client_id : String,
                               @Header("Authorization") authorization : String,
                               @Query("fields") fields: String = "id,name,platforms.name,genres.name,involved_companies.company.name,age_ratings.category,age_ratings.rating,release_dates.y,cover.url,summary",
                               @Query("search") name : String,
    @Body body : String = "where age_ratings.rating <= 4"): Response<List<Game>>

}