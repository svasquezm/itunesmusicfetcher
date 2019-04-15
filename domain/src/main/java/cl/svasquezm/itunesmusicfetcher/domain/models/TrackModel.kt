package cl.svasquezm.itunesmusicfetcher.domain.models

/**
 * Domain model to represent a Track
 */
open class TrackModel(
    open var trackId: Long,
    open var artistName: String?,
    open var collectionName: String?,
    open var trackName: String?,
    open var previewUrl: String?,
    open var isStreamable: Boolean,
    open var isFavorite: Boolean
)