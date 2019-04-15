package cl.svasquezm.itunesmusicfetcher.data.repositories

import cl.svasquezm.itunesmusicfetcher.data.base.ITrackLocalSource
import cl.svasquezm.itunesmusicfetcher.data.base.ITrackRemoteSource
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel

/**
 * Repository to manage Tracks data local and remote
 */
class TrackRepository(private val localSource: ITrackLocalSource,
                      private val remoteSource: ITrackRemoteSource) {

    /**
     * Persists a new Track locally
     */
    fun addLocalTrack(vararg track: TrackModel) {
        localSource.addTracks(*track)
    }

    /**
     * Get all local tracks from local storage
     */
    fun getAllLocalTracks() = localSource.getAllTracks()

    /**
     * Get all local favorite tracks from local storage
     */
    fun getAllFavoriteTracks() = localSource.getAllFavoriteTracks()

    /**
     * Performs a remote call to server an retrieves a list of tracks with
     * `term` filter. If operation was successfully, onSuccess() callback will
     * be invoked, otherwise it invokes onFailed()
     */
    fun getRemoteTracks(term: String,
                        onSuccess: (tracks: List<TrackModel>) -> Unit,
                        onFailed: () -> Unit) = remoteSource.getTracks(term, onSuccess, onFailed)

    fun updateFavorite(trackId: Long, isFavorite: Boolean){
        localSource.updateFavorite(trackId, isFavorite)
    }

    /**
     * Remove all Tracks that are not favorites
     */
    fun deleteAllPreviousNonFavorites(){
        localSource.deleteNonFavorites()
    }
}