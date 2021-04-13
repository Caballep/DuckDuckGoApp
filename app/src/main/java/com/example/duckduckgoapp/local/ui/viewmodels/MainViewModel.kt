package com.example.duckduckgoapp.local.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duckduckgoapp.local.models.Character
import com.example.duckduckgoapp.local.repositories.DuckDuckServiceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val duckDuckServiceRepository: DuckDuckServiceRepository
) : ViewModel() {

    private lateinit var originalCharacterList: List<Character>
//    val defaultLastSearch = "Type here"
//    var lastSeach = defaultLastSearch

    private val mutableCharactersState =
        MutableStateFlow<CharactersState>(CharactersState.BLANK)
    val charactersState: StateFlow<CharactersState>
        get() = mutableCharactersState

    private val mutableSelectedCharacterState =
        MutableStateFlow<SelectedCharacterState>(SelectedCharacterState.UNSELECTED)
    val selectedCharacterState: StateFlow<SelectedCharacterState>
        get() = mutableSelectedCharacterState

    fun fetchCharacters() {
        viewModelScope.launch {
            duckDuckServiceRepository.getCharacters().onStart {
                mutableCharactersState.value = CharactersState.LOADING
            }.catch { error ->
                // It would be better to have custom Exceptions instead of a plan error String
                error.message?.let {
                    mutableCharactersState.value = CharactersState.ERROR(it)
                }
            }.collect {
                originalCharacterList = it.toCharacterList()
                mutableCharactersState.value = CharactersState.SUCCESS(originalCharacterList)
            }
        }
    }

    fun selectCharacter(character: Character) {
        mutableSelectedCharacterState.value = SelectedCharacterState.SELECTED(character)
    }

    fun filterAndRemakeCharacterList(text: String) {
        if (text.isEmpty()) {
            mutableCharactersState.value = CharactersState.FILTERED(originalCharacterList)
//            lastSeach = defaultLastSearch
        } else {
            val filteredCharacterList = originalCharacterList.filter {
                it.name.contains(text, ignoreCase = true) || it.description.contains(
                    text,
                    ignoreCase = true
                )
            }
            mutableCharactersState.value = CharactersState.FILTERED(filteredCharacterList)
//            lastSeach = text
        }
    }

//    fun hasCharactersData() = !originalCharacterList.isNullOrEmpty()

    sealed class CharactersState {
        data class SUCCESS(val characters: List<Character>) : CharactersState()
        object LOADING : CharactersState()
        data class ERROR(val message: String) : CharactersState()
        object BLANK : CharactersState()
        data class FILTERED(val characters: List<Character>) : CharactersState()
    }

    sealed class SelectedCharacterState {
        data class SELECTED(val character: Character) : SelectedCharacterState()
        object UNSELECTED : SelectedCharacterState()
    }
}