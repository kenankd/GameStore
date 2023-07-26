package ba.etf.rma23.projekat.data.repositories

import androidx.room.*

@Dao
interface GameReviewDao {

    @Query("SELECT * FROM GameReview WHERE online=0")
    suspend fun getOfflineReviews():List<GameReview>

    @Query("UPDATE GameReview SET online = 1 WHERE id = :id")
    suspend fun setReviewOnline(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameReview(vararg gameReview: GameReview)
}