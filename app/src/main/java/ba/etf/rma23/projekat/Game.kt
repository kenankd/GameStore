package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.UserImpression
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.URL

data class Game(
    val id: Long,
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platform: String,
    @SerializedName("release_dates") val release_date: String,
    @SerializedName("age_ratings.rating") val rating: Double,
    val cover: String,
    @SerializedName("age_ratings.rating") val esrbRating: String,
    @SerializedName("involved_companies.name") val developer: String,
    @SerializedName("involved_companies.name") val publisher: String,
    @SerializedName("genre") val genre: String,
    val summary: String,
    val userImpressions: List<UserImpression>,
)
class GameDeserializer : JsonDeserializer<Game> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Game {
        val jsonObject = json?.asJsonObject
        val id = jsonObject?.get("id")?.asLong ?: 0L
        val name = jsonObject?.get("name")?.asString ?: ""
        val platforms = jsonObject?.getAsJsonArray("platforms")?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
        val firstReleaseDate = jsonObject?.getAsJsonArray("release_dates")?.get(0)?.asJsonObject?.get("y")?.asString ?: ""
        val ageRatings = jsonObject?.getAsJsonArray("age_ratings")?.get(0)?.asJsonObject?.get("rating")?.asDouble ?: 0.0
        val coverUrl = jsonObject?.get("cover")?.asJsonObject?.get("url")?.toString() ?: ""
        var esrbRating : String = "unknown"
        if(jsonObject?.getAsJsonArray("age_ratings")?.get(0)?.asJsonObject?.get("category")?.asInt == 1 )
            esrbRating = ESRB[jsonObject?.getAsJsonArray("age_ratings")?.get(0)?.asJsonObject?.get("rating")?.asInt!!].toString()
        val developer = jsonObject?.getAsJsonArray("involved_companies")?.get(0)?.asJsonObject?.get("company")?.asJsonObject?.get("name")?.asString ?: ""
        val publisher = developer //jsonObject?.getAsJsonArray("involved_companies")?.get(0)?.asJsonObject?.get("company")?.asJsonObject?.get("name")?.asString ?: ""
        val genres = jsonObject?.getAsJsonArray("genres")?.get(0)?.asJsonObject?.get("name")?.asString ?: ""
        val summary = jsonObject?.get("summary")?.asString ?: ""
        val userImpressions = emptyList<UserImpression>()
        return Game(id, name, platforms, firstReleaseDate, ageRatings, coverUrl, esrbRating, developer, publisher, genres, summary, userImpressions)
    }
}