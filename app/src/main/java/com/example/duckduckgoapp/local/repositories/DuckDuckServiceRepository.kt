package com.example.duckduckgoapp.local.repositories

import com.example.duckduckgoapp.remote.entities.CharactersResponse
import com.example.duckduckgoapp.remote.services.DuckDuckService
import com.example.duckduckgoapp.local.utils.FlavorConfigHelper
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
                    q = FlavorConfigHelper.getCharacterServiceQParameter()
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}