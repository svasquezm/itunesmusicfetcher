package cl.svasquezm.itunesmusicfetcher.framework.network.services

import cl.svasquezm.itunesmusicfetcher.framework.models.retrofitmodels.TrackResultsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    // https://itunes.apple.com/
    @GET("search?term=metallica")
    fun tracks(@Query("term") term: String): Call<TrackResultsList>
}