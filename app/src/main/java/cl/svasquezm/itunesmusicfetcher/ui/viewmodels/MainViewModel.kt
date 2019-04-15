package cl.svasquezm.itunesmusicfetcher.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.svasquezm.itunesmusicfetcher.data.repositories.TrackRepository
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import cl.svasquezm.itunesmusicfetcher.framework.utils.ItemAdapterPayload
import cl.svasquezm.itunesmusicfetcher.framework.utils.PlayMediaHelper

class MainViewModel(private val trackRepository: TrackRepository,
                    private val playMediaHelper: PlayMediaHelper
) : ViewModel() {

    private val tracksLiveData = MutableLiveData<List<TrackModel>>()
    init {
        getLocalTracks()
    }

    fun getTracks() = tracksLiveData

    /**
     * Retrieve all stored tracks from Room's database
     */
    fun getLocalTracks() { tracksLiveData.value = trackRepository.getAllLocalTracks() }

    /**
     * Retrieve all favorite stored tracks from Room's database
     */
    fun getAllFavoriteTracks() { tracksLiveData.value = trackRepository.getAllFavoriteTracks() }

    /**
     * Retrieve tracks by a given term
     */
    fun getRemoteTracks(term: String = "", onSuccess: () -> Unit, onFailed: () -> Unit){
        trackRepository.getRemoteTracks(term, onSuccess = {
            tracksLiveData.value = it
            trackRepository.addLocalTrack(*(it.toTypedArray()))
            onSuccess()

        }, onFailed = onFailed)
    }

    fun selectOrDeselectTackAsFavorite(track: TrackModel){
        track.isFavorite = !track.isFavorite
        trackRepository.updateFavorite(track.trackId, track.isFavorite)
    }

    fun playOrStopASong(track: TrackModel, callback: (ItemAdapterPayload) -> Unit, onFailed: () -> Unit) {

        // Invoke fallback if no preview is available
        if(track.previewUrl == null) {
            onFailed()
        } else {
            playMediaHelper.playOrStopMedia(track.previewUrl, callback)
        }
    }
}