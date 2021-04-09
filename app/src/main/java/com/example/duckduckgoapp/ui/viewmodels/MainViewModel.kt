package com.example.duckduckgoapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duckduckgoapp.entities.Character
import com.example.duckduckgoapp.network.toCharacterList
import com.example.duckduckgoapp.repositories.DuckDuckServiceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val duckDuckServiceRepository: DuckDuckServiceRepository
) : ViewModel() {

    private val mutableMainState =
        MutableStateFlow<MainState>(MainState.BLANK)
    val mainState: StateFlow<MainState>
        get() = mutableMainState

    fun fetchCharacters() {
        viewModelScope.launch {
            duckDuckServiceRepository.getCharacters().onStart {
                mutableMainState.value = MainState.LOADING
            }.catch { error ->
                // It would be better to have custom Exceptions instead of a plan error String
                error.message?.let {
                    mutableMainState.value = MainState.ERROR(it)
                }
            }.collect {
                mutableMainState.value = MainState.SUCCESS(it.toCharacterList())
            }
        }
    }

    sealed class MainState {
        data class SUCCESS(val characters: List<Character>) : MainState()
        object LOADING : MainState()
        data class ERROR(val message: String) : MainState()
        object BLANK : MainState()
    }
}