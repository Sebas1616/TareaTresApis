package com.example.tareatresapis.data.repository

import com.example.tareatresapis.data.remote.RickAndMortyApiService
import com.example.tareatresapis.data.remote.dto.CharacterDto

class RickRepository(private val api: RickAndMortyApiService) {
    suspend fun fetchCharacters(): List<CharacterDto> {
        return try {
            val response = api.getCharacters()
            if (response.isSuccessful) response.body()?.results.orEmpty() else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
