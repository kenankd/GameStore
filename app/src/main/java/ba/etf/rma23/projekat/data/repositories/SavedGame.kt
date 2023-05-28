package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class SavedGame(
    @SerializedName("igdb_id") val id : Long,
    @SerializedName("name") val title : String
)
