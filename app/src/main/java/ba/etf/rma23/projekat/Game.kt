package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import ba.etf.rma23.projekat.UserImpression
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.lang.reflect.Type
import java.net.URL
data class Game(
    val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platform: String,
    @SerializedName("release_dates") val releaseDate: String,
    @SerializedName("age_ratings.rating") val rating: Double,
    val cover: String,
    @SerializedName("age_ratings") val esrbRating: String,
    @SerializedName("involved_companies") val developer: String,
    @SerializedName("involved_companies.name") val publisher: String,
    @SerializedName("genre") val genre: String,
    val summary: String,
    val userImpressions: List<UserImpression>
)
class GameDeserializer : JsonDeserializer<Game> {
    @SuppressLint("SuspiciousIndentation")
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Game {
        val jsonObject = if(json is JsonArray)
            json.get(0)?.asJsonObject!!
        else
            json?.asJsonObject!!
        val id = jsonObject.get("id")?.asInt ?: 0
        val name = jsonObject.get("name")?.asString ?: ""
        val platforms = jsonObject.getAsJsonArray("platforms")?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
        var esrbRating : String = "unknown"
        var ageRating : Double = 0.0
        val firstReleaseDate = jsonObject.getAsJsonArray("release_dates")?.get(0)?.asJsonObject?.get("human")?.asString ?: ""
        val ageRatingsArray = jsonObject.getAsJsonArray("age_ratings")
        if(ageRatingsArray!=null && ageRatingsArray.size()>0){
            for(element in ageRatingsArray){
                if(ageRating == 0.0)
                    ageRating = element.asJsonObject.get("rating").asDouble
                val objekat = element.asJsonObject.get("category").asInt ;
                if( objekat == 1){
                    esrbRating = ESRB[ageRating.toInt()].toString()
                }
            }
            if(esrbRating == "unknown"){
                for(element in ageRatingsArray){
                    val objekat = element.asJsonObject.get("category").asInt ;
                    if( objekat == 2)
                        esrbRating = ESRB[element.asJsonObject.get("rating").asInt].toString()
                }
            }
        }
        val coverUrl = jsonObject.get("cover")?.asJsonObject?.get("url")?.toString() ?: ""
        val developer = jsonObject.getAsJsonArray("involved_companies")?.get(0)?.asJsonObject?.get("company")?.asJsonObject?.get("name")?.asString ?: ""
        val publisher = developer
        val genres = jsonObject.getAsJsonArray("genres")?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
        val summary = jsonObject.get("summary")?.asString ?: ""
        val userImpressions = emptyList<UserImpression>()
        return Game(id, name, platforms, firstReleaseDate, ageRating, coverUrl, esrbRating, developer, publisher, genres, summary, userImpressions)
    }
}