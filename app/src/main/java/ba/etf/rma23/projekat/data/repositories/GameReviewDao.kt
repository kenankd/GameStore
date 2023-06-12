package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameReviewDao {

    @Query("SELECT * FROM GameReview WHERE online=0")
    suspend fun getOfflineReviews():List<GameReview>

    @Query("UPDATE GameReview SET online = 1 WHERE id = :id")
    suspend fun setReviewOnline(id: Int)

    @Insert
    suspend fun insertGameReview(gameReview: GameReview)
}