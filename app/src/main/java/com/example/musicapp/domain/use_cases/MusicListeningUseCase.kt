package com.example.musicapp.domain.use_cases

data class MusicListeningUseCases(
    val addMusicListingUseCase: AddMusicListingUseCase,
    val removeMusicListingUseCase: RemoveMusicListingUseCase,
    val getMusicListeningUseCase: GetMusicListeningUseCase,
    val addToMemoryUseCase: AddToMemoryUseCase,
    val removeFromMemoryUseCase: RemoveFromMemoryUseCase
) {

}