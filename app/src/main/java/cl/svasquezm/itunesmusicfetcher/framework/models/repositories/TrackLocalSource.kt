package cl.svasquezm.itunesmusicfetcher.framework.models.repositories

import cl.svasquezm.itunesmusicfetcher.data.base.ITrackLocalSource
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import cl.svasquezm.itunesmusicfetcher.framework.models.database.TrackDatabase
import cl.svasquezm.itunesmusicfetcher.framework.toRoomTrackModel

class TrackLocalSource(private val db: TrackDatabase) : ITrackLocalSource {

    override fun deleteNonFavorites() {
        db.dao().deleteNonFavorites()
    }

    override fun addTracks(vararg tracks: TrackModel) {
        db.dao().insertAll(*tracks.map{ it.toRoomTrackModel() }.toTypedArray())
    }

    override fun getAllTracks(): List<TrackModel> {
        return db.dao().getAllRoomTracks()
    }

    override fun getAllFavoriteTracks(): List<TrackModel> {
        return db.dao().getAllFavoriteRoomTracks()
    }

    override fun updateFavorite(trackId: Long, isFavorite: Boolean) {
        db.dao().updateIsFavorite(trackId, isFavorite)
    }
}