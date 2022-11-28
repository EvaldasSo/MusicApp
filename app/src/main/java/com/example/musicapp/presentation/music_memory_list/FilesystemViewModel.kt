package com.example.musicapp.presentation.music_memory_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.common.Resource
import com.example.musicapp.domain.model.MusicListing
import com.example.musicapp.domain.use_cases.MusicListeningUseCases
import com.example.musicapp.presentation.components.MusicListingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesystemViewModel @Inject constructor(
    private val musicListingUseCases: MusicListeningUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MusicListingState())
    val state: StateFlow<MusicListingState> = _state.asStateFlow()

    init {
        getMusicListing()
    }

    private fun getMusicListing() {
        musicListingUseCases.getMusicListeningUseCase(true).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data ?: emptyList()
                    val newState = !_state.value.isReload
                    _state.update { it.copy(music = data, isReload = newState, isLoading = false) }
                }
                is Resource.Error -> {
                    _state.value = MusicListingState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = MusicListingState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun saveToDatabase(musicListing: MusicListing) {
        musicListingUseCases.addMusicListingUseCase(musicListing)
    }

    private suspend fun removeFromDatabase(musicListing: MusicListing) {
        musicListingUseCases.removeMusicListingUseCase(musicListing)
    }

    fun processEditAction(musicListing: MusicListing) {
        viewModelScope.launch {
            val findItem: MusicListing? = _state.value.music.find { it.id == musicListing.id }
            findItem?.let { item ->
                if(item.isStoredInDb) {
                    removeFromDatabase(musicListing)
                }  else {
                    saveToDatabase(musicListing)
                }

                _state.value.music.forEach { it ->
                    if (it.id == musicListing.id) it.isStoredInDb = !it.isStoredInDb
                }

                //TODO::FIXME List item value is updated with new value, but recomposition is not called...
                val newState = !_state.value.isReload
                _state.update { it.copy(music = _state.value.music, isReload = newState, isLoading = false) }
            }
        }
    }
}