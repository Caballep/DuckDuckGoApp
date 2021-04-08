package com.example.duckduckgoapp.repositories

import com.example.duckduckgoapp.network.CharactersResponse
import com.example.duckduckgoapp.network.DuckDuckService
import com.example.duckduckgoapp.utils.FlavorConfigProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DuckDuckServiceRepository @Inject constructor(
    private val duckDuckService: DuckDuckService
) {
    suspend fun getCharacters(): Flow<CharactersResponse> {
        return flow {
            emit(
                duckDuckService.getCharacters(
                    q = FlavorConfigProvider.getCharacterServiceQParameter()
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}