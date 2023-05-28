package ba.etf.rma23.projekat.data.repositories

data class TokenData(
    private val access_token : String,
    private val expires_in : Int,
    private val token_type : String
)
