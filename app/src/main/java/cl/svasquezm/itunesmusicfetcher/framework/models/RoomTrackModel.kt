package cl.svasquezm.itunesmusicfetcher.framework.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import com.google.gson.annotations.SerializedName

@Entity
class RoomTrackModel(
    @PrimaryKey
    @SerializedName("trackId")
    var uid: Long,

    @ColumnInfo(name = "artist_name")
    override var artistName: String?,

    @ColumnInfo(name = "collection_name")
    override var collectionName: String?,

    @ColumnInfo(name = "track_name")
    override var trackName: String?,

    @ColumnInfo(name = "preview_url")
    override var previewUrl: String?,

    @ColumnInfo(name = "is_streamable")
    override var isStreamable: Boolean,

    @ColumnInfo(name = "is_favorite")
    override var isFavorite: Boolean) : TrackModel(
        uid,
        artistName,
        collectionName,
        trackName,
        previewUrl,
        isStreamable,
        isFavorite)
