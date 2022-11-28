package com.example.musicapp.domain.model

data class MusicListing(
    val id: Int,
    val category: String,
    val description: String,
    val thumbnail: String,
    val title: String,
    var isStoredInMemory: Boolean = false,
    var isStoredInDb: Boolean = false,
    val sizeInMb: Int,
    val timeInSec: Int
)