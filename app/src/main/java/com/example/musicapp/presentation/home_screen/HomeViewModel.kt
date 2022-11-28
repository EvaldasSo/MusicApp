package com.example.musicapp.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.common.Resource
import com.example.musicapp.domain.use_cases.MusicListeningUseCases
import com.example.musicapp.presentation.components.MusicListingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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
}
