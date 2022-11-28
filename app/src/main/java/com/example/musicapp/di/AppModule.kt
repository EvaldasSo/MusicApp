package com.example.musicapp.di

import android.app.Application
import androidx.room.Room
import com.example.musicapp.common.Constants
import com.example.musicapp.data.local.MusicDatabase
import com.example.musicapp.data.memory.MusicListeningTemporarySource
import com.example.musicapp.data.remote.DummyJsonApi
import com.example.musicapp.data.repository.MusicRepositoryImpl
import com.example.musicapp.domain.repository.MusicRepository
import com.example.musicapp.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSpotifyApi(): DummyJsonApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DummyJsonApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicRepository(
        api: DummyJsonApi,
        db: MusicDatabase,
    ): MusicRepository {
        return MusicRepositoryImpl(api = api, db.dao, musicListeningTemporarySource())
    }

    @Provides
    @Singleton
    fun provideMusicDatabase(app: Application): MusicDatabase {
        return Room.databaseBuilder(
            app, MusicDatabase::class.java, "music_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicListingUseCases(repository: MusicRepository): MusicListeningUseCases {
        return MusicListeningUseCases(
            getMusicListeningUseCase = GetMusicListeningUseCase(repository),
            addMusicListingUseCase = AddMusicListingUseCase(repository),
            removeMusicListingUseCase = RemoveMusicListingUseCase(repository),
            addToMemoryUseCase = AddToMemoryUseCase(repository),
            removeFromMemoryUseCase = RemoveFromMemoryUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun musicListeningTemporarySource() = MusicListeningTemporarySource()
}