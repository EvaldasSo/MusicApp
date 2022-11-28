package com.example.musicapp.presentation.music_filesystem_list

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
class MemoryViewModel @Inject constructor(
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
                    _state.value =
                        MusicListingState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = MusicListingState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun addToMemory(musicListing: MusicListing) {
        musicListingUseCases.addToMemoryUseCase(musicListing)
    }

    private suspend fun removeFromMemory(musicListing: MusicListing) {
        musicListingUseCases.removeFromMemoryUseCase(musicListing)
    }

    fun processEditAction(musicListing: MusicListing) {
        viewModelScope.launch {
            val findItem: MusicListing? = _state.value.music.find { it.id == musicListing.id }
            findItem?.let { item ->
                if (item.isStoredInMemory) {
                    removeFromMemory(musicListing)
                } else {
                    addToMemory(musicListing)
                }

                _state.value.music.forEach { it ->
                    if (it.id == musicListing.id) it.isStoredInMemory = !it.isStoredInMemory
                }

                //TODO::FIXME List item value is updated with new value, but recomposition is not called...
                //_state.value = MusicListingState(music = _state.value.music)
                val newState = !_state.value.isReload
                _state.update {
                    it.copy(
                        music = _state.value.music,
                        isReload = newState,
                        isLoading = false
                    )
                }
            }
        }
    }

}