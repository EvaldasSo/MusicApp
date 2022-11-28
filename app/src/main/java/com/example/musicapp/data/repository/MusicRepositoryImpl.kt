package com.example.musicapp.data.repository


import com.example.musicapp.common.Resource
import com.example.musicapp.data.local.MusicDao

import com.example.musicapp.data.mapper.toMusicListening
import com.example.musicapp.data.mapper.toMusicListeningEntity
import com.example.musicapp.data.memory.MusicListeningTemporarySource
import com.example.musicapp.data.remote.DummyJsonApi
import com.example.musicapp.domain.model.MusicListing
import com.example.musicapp.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicRepositoryImpl @Inject constructor(
    private val api: DummyJsonApi,
    private val dao: MusicDao,
    private val tempList: MusicListeningTemporarySource
) : MusicRepository {


    override fun getMusicListening(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<MusicListing>>> = flow {
        emit(Resource.Loading())

        //Let's do Ssot jk :D
        val combinedList: ArrayList<MusicListing> = arrayListOf()
        combinedList.addAll(tempList.getList())
        combinedList.filter { !it.isStoredInMemory }.forEach { it.isStoredInMemory = true }

        val localListing = dao.getAllMusicListening().map { it.toMusicListening() }

        val aIds = combinedList.mapTo(HashSet(combinedList.size)) { it.id }
        for (item in localListing) {
            if (item.id !in aIds) combinedList.add(item)
        }

        emit(Resource.Success(data = combinedList))

        try {
            val remoteListing = api.getMusicListening().products.map { it.toMusicListening() }
            val combinedIds = combinedList.mapTo(HashSet(combinedList.size)) { it.id }
            for (item in remoteListing) {
                if (item.id !in combinedIds) combinedList.add(item)
            }
            emit(Resource.Success(combinedList))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data"))
        }
    }

    override suspend fun insertMusicListing(musicListing: MusicListing) {
        dao.insertMusicListening(musicListing.toMusicListeningEntity())
    }

    override suspend fun removeMusicListing(musicListing: MusicListing) {
        dao.deleteById(musicListing.id)
    }

    override suspend fun addToMemoryMusicListing(musicListing: MusicListing) {
        tempList.save(musicListing)
    }

    override suspend fun removeFromMemoryMusicListing(musicListing: MusicListing) {
        tempList.remove(musicListing)
    }

}