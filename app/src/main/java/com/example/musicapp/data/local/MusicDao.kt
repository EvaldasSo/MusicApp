package com.example.musicapp.data.local

import androidx.room.*

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusicListeningList(
        musicListingEntity: List<MusicListingEntity>
    )

    @Query("DELETE FROM musiclistingentity")
    suspend fun clearMusicListening()

    @Query("SELECT * FROM musiclistingentity")
    suspend fun getAllMusicListening(): List<MusicListingEntity>

    @Delete
    suspend fun removeMusicListening(
        musicListingEntity: MusicListingEntity
    )

    @Query("DELETE FROM musiclistingentity WHERE id = :musicId")
    suspend fun deleteById(musicId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusicListening(
        musicListingEntity: MusicListingEntity
    )

}