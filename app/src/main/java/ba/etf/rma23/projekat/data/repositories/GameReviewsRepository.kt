package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getSavedGames
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.isGameSaved
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.saveGame
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import ba.etf.rma23.projekat.data.repositories.GamesRepository.savedGames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


object GameReviewsRepository {
    suspend fun getOfflineReviews(context : Context):List<GameReview>{
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            return@withContext db.gameReviewDao().getOfflineReviews()
        }
    }
    suspend fun sendOfflineReviews(context: Context): Int{
        return withContext(Dispatchers.IO){
            if( (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork == null) return@withContext 0
            var number=0
            val db = AppDatabase.getInstance(context)
            val reviews = db.gameReviewDao().getOfflineReviews()
            for(review in reviews){
                if(sendReview(context,review)){
                    number++
                    db.gameReviewDao().setReviewOnline(review.id)
                }
            }
            return@withContext reviews.size
        }
    }
    //promijeniti funkciju beta verzija
    suspend fun sendReview(context: Context,gameReview: GameReview):Boolean{
        return withContext(Dispatchers.IO) {
            try {
                var isSaved = false
                for (game in getSavedGames())
                    if (gameReview.igdb_id == game.id)
                        isSaved = true
                    if (!isSaved)
                        saveGame(getGameById(gameReview.igdb_id)!!)
                val gameJsonObject = JSONObject()
                if (gameReview.review != null)
                    gameJsonObject.put("review", gameReview.review)
                if (gameReview.rating != null)
                    gameJsonObject.put("rating", gameReview.rating)
                val mediaType = "application/json".toMediaTypeOrNull()
                val response = AccountAPIConfig.retrofit.sendReview(
                   gid = gameReview.igdb_id,
                     gameReview = gameJsonObject.toString().toRequestBody(mediaType)
                )
                return@withContext true
            } catch (e: Exception) {
            val db = AppDatabase.getInstance(context)
            gameReview.online = false //mozda nije potrebno
            db.gameReviewDao().insertGameReview(gameReview)
            return@withContext false
            }
        }
    }
    suspend fun getReviewsForGame(igdb_id : Int):List<GameReview>{
        return withContext(Dispatchers.IO) {
            return@withContext AccountAPIConfig.retrofit.getReviewsForGame(igdb_id).body() ?: return@withContext listOf<GameReview>()
        }
    }
}