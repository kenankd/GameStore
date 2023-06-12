package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.isGameSaved
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.saveGame
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


object GameReviewsRepository {
    //mozda se ne smije slati context kao parametar
    suspend fun getOfflineReviews(context : Context):List<GameReview>{
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            return@withContext db.gameReviewDao().getOfflineReviews()
        }
    }
    suspend fun sendOfflineReviews(context: Context): Int{
        return withContext(Dispatchers.IO){
            var number=0
            val db = AppDatabase.getInstance(context)
            val reviews = db.gameReviewDao().getOfflineReviews()
            for(review in reviews){
                if(sendReview(review,context)){
                    number++
                    db.gameReviewDao().setReviewOnline(review.id)
                }
            }
            return@withContext reviews.size
        }
    }
    //promijeniti funkciju beta verzija
    suspend fun sendReview(gameReview: GameReview, context: Context):Boolean{
        return withContext(Dispatchers.IO){
            //ne mora biti non null - prepraviti
            val game = getGameById(gameReview.igdb_id)!!
            if(!isGameSaved(game.id))
                saveGame(game)
            val gameJsonObject = JSONObject()
            if(gameReview.review!=null)
                gameJsonObject.put("review",gameReview.review)
            if(gameReview.rating!=null)
                gameJsonObject.put("rating",gameReview.rating)
            val mediaType = "application/json".toMediaTypeOrNull()
            AccountAPIConfig.retrofit.sendReview(gid= gameReview.igdb_id,gameReview = gameJsonObject.toString().toRequestBody(mediaType))
            return@withContext true
        }
    }
    suspend fun getReviewsForGame(igdb_id : Int):List<GameReview>{
        return withContext(Dispatchers.IO) {
            return@withContext AccountAPIConfig.retrofit.getReviewsForGame(igdb_id).body() ?: return@withContext listOf<GameReview>()
        }
    }
}