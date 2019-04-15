package cl.svasquezm.itunesmusicfetcher.framework.models.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.svasquezm.itunesmusicfetcher.framework.models.RoomTrackModel

@Dao
interface RoomTrackDao {

    @Query("SELECT * FROM roomtrackmodel WHERE uid = :trackId")
    fun getTrack(trackId: String): LiveData<RoomTrackModel>

    @Query("SELECT * FROM roomtrackmodel")
    fun getAllRoomTracks(): List<RoomTrackModel>

    @Query("SELECT * FROM roomtrackmodel WHERE is_favorite = 1")
    fun getAllFavoriteRoomTracks(): List<RoomTrackModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg trackModels: RoomTrackModel)

    @Query("UPDATE roomtrackmodel SET is_favorite = :isFavorite WHERE uid = :trackId")
    fun updateIsFavorite(trackId: Long, isFavorite: Boolean): Int

    @Query("DELETE from roomtrackmodel WHERE is_favorite = 0")
    fun deleteNonFavorites()
}