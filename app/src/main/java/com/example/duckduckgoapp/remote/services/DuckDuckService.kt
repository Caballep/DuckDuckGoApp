package com.example.duckduckgoapp.remote.services

import com.example.duckduckgoapp.remote.entities.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckDuckService {

    @GET("/")
    suspend fun getCharacters(
        @Query("q", encoded = true) q: String,
        @Query("amp;format", encoded = true) format: String = "json"
    ): CharactersResponse

}
