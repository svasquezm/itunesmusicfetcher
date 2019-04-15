package cl.svasquezm.itunesmusicfetcher.framework.models.repositories

import cl.svasquezm.itunesmusicfetcher.data.base.ITrackRemoteSource
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import cl.svasquezm.itunesmusicfetcher.framework.models.retrofitmodels.TrackResultsList
import cl.svasquezm.itunesmusicfetcher.framework.network.services.ITunesService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrackRemoteSource : ITrackRemoteSource {
    override fun getTracks(
        term: String,
        onSuccess: (tracks: List<TrackModel>) -> Unit,
        onFailed: () -> Unit
    ) {
        // create an instance of gson to be used when building our service
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofit.create(ITunesService::class.java)
        service.tracks(term).enqueue(object : Callback<TrackResultsList>{
            override fun onResponse(call: Call<TrackResultsList>, response: Response<TrackResultsList>) {
                onSuccess(response.body()!!.results)
            }

            override fun onFailure(call: Call<TrackResultsList>, t: Throwable) {
                onFailed()
            }
        })
    }
}