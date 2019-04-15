package cl.svasquezm.itunesmusicfetcher.framework

import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import cl.svasquezm.itunesmusicfetcher.framework.models.RoomTrackModel

fun TrackModel.toRoomTrackModel() : RoomTrackModel {
    return RoomTrackModel(
        uid = this.trackId,
        artistName = this.artistName?: "",
        collectionName = this.collectionName?: "",
        trackName = this.trackName?: "",
        previewUrl = this.previewUrl?: "",
        isStreamable = this.isStreamable,
        isFavorite = false
    )
}