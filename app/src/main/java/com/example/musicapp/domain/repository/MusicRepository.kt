package com.example.musicapp.domain.repository

import com.example.musicapp.common.Resource
import com.example.musicapp.domain.model.MusicListing
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    fun getMusicListening(fetchFromRemote: Boolean): Flow<Resource<List<MusicListing>>>

    suspend fun insertMusicListing(musicListing: MusicListing)

    suspend fun removeMusicListing(musicListing: MusicListing)

    suspend fun addToMemoryMusicListing(musicListing: MusicListing)

    suspend fun removeFromMemoryMusicListing(musicListing: MusicListing)

}