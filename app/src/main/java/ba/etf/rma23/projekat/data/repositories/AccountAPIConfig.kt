package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccountAPIConfig {

    private val BASE_URL = "https://rma23ws.onrender.com/account/"
    val retrofit : AccountApi = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create())
        .build()
        .create(AccountApi::class.java)
}