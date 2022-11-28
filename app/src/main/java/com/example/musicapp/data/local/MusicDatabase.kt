package com.example.musicapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MusicListingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {
    abstract val dao: MusicDao
}