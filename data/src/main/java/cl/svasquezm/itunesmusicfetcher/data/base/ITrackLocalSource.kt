package cl.svasquezm.itunesmusicfetcher.data.base
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel

/**
 * Abstract definition to manage local Track data
 */
interface ITrackLocalSource {
    fun deleteNonFavorites()
    fun addTracks(vararg tracks: TrackModel)
    fun getAllTracks(): List<TrackModel>
    fun getAllFavoriteTracks(): List<TrackModel>
    fun updateFavorite(trackId: Long, isFavorite: Boolean)
}