package com.example.musicapp.data.memory

import com.example.musicapp.domain.model.MusicListing

class MusicListeningTemporarySource {

    companion object {
        var cache: MutableList<MusicListing> = mutableListOf()
    }

    fun save(musicList: MusicListing) {
        if (!cache.contains(musicList))
            cache.add(musicList)
    }

    fun remove(musicList: MusicListing) {
        cache.removeIf { it.id == musicList.id }
    }

    fun getSize(): Int {
        return cache.size
    }

    fun getList(): List<MusicListing> {
        return cache.toList()
    }
}