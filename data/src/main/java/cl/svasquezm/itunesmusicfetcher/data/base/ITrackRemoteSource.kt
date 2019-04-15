package cl.svasquezm.itunesmusicfetcher.data.base

import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel

/**
 * Abstract definition to manage network Track data
 */
interface ITrackRemoteSource {
    fun getTracks(term: String,
                  onSuccess: (tracks: List<TrackModel>) -> Unit,
                  onFailed: () -> Unit)
}