package ba.etf.rma23.projekat

data class UserReview (
    override var username: String,
    override val timestamp: Long,
    val review: String
):UserImpression(username,timestamp)


