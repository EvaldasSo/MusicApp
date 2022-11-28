package com.example.musicapp.domain.use_cases

import com.example.musicapp.common.Resource
import com.example.musicapp.domain.model.MusicListing
import com.example.musicapp.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetMusicListeningUseCase @Inject constructor(
    private val repository: MusicRepository
) {
    operator fun invoke(fetchFromApi: Boolean): Flow<Resource<List<MusicListing>>> {
        return repository.getMusicListening(fetchFromApi)
    }

}