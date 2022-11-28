package com.example.musicapp.presentation.components


import com.example.musicapp.domain.model.MusicListing

data class MusicListingState(
    val isLoading: Boolean = false,
    val music: List<MusicListing> = emptyList(),
    val error: String = "",
    val isReload: Boolean = false
) {
}