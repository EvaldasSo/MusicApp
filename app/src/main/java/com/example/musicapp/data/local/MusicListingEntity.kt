package com.example.musicapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity
data class MusicListingEntity(
    @PrimaryKey val id: Int,
    val category: String,
    val description: String,
    val thumbnail: String,
    val title: String,
    val isStoredInDb: Boolean = true,
    val isStoredInMemory: Boolean = false,
    val sizeInMb: Int = Random.nextInt(1, 10),
    val timeInSec: Int = Random.nextInt(10, 300)
) {
}