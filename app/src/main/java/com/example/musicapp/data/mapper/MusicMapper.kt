package com.example.musicapp.data.mapper

import com.example.musicapp.data.local.MusicListingEntity
import com.example.musicapp.data.memory.MusicListeningTemporarySource
import com.example.musicapp.data.remote.dto.ProductDto
import com.example.musicapp.domain.model.MusicListing
import kotlin.random.Random


fun MusicListingEntity.toMusicListening(): MusicListing {
    return MusicListing(
        id = id,
        category = category,
        description = description,
        thumbnail = thumbnail,
        title = title,
        isStoredInDb = isStoredInDb,
        isStoredInMemory = isStoredInMemory,
        sizeInMb = sizeInMb,
        timeInSec = timeInSec
    )
}

fun MusicListing.toMusicListeningEntity(): MusicListingEntity {
    return MusicListingEntity(
        id = id,
        category = category,
        description = description,
        thumbnail = thumbnail,
        title = title,
        isStoredInDb = true,
        isStoredInMemory = isStoredInMemory,
        sizeInMb = sizeInMb,
        timeInSec = timeInSec
    )
}

fun ProductDto.toMusicListeningEntity(): MusicListingEntity {
    return MusicListingEntity(
        id = id,
        category = category,
        description = description,
        thumbnail = thumbnail,
        title = title
    )
}

fun ProductDto.toMusicListening(): MusicListing {
    return MusicListing(
        id = id,
        category = category,
        description = description,
        thumbnail = thumbnail,
        title = title,
        isStoredInDb = false,
        isStoredInMemory = false,
        sizeInMb = Random.nextInt(1, 10),
        timeInSec = Random.nextInt(10, 300)
    )
}