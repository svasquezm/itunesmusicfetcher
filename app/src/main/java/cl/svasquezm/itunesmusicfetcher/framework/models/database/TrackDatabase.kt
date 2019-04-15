package cl.svasquezm.itunesmusicfetcher.framework.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.svasquezm.itunesmusicfetcher.framework.models.RoomTrackModel
import cl.svasquezm.itunesmusicfetcher.framework.models.daos.RoomTrackDao

@Database(entities = [RoomTrackModel::class], version = 1)
abstract class TrackDatabase : RoomDatabase(){
    abstract fun dao() : RoomTrackDao
}