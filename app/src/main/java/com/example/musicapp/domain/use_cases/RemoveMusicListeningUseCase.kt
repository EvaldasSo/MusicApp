package com.example.musicapp.domain.use_cases

import com.example.musicapp.domain.model.MusicListing
import com.example.musicapp.domain.repository.MusicRepository

import javax.inject.Inject

class RemoveMusicListingUseCase @Inject constructor(
    private val repository: MusicRepository
) {
    suspend operator fun invoke(musicListing: MusicListing) {
        return repository.removeMusicListing(musicListing)
    }

}