package com.example.tareatresapis.data.repository

import com.example.tareatresapis.data.mapper.PokemonDomain
import com.example.tareatresapis.data.mapper.toDomain
import com.example.tareatresapis.data.remote.PokeApiService

class PokemonRepository(private val api: PokeApiService) {
    suspend fun fetchPokemon(): PokemonDomain? {
        return try {
            val response = api.getPokemonPrueba()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain()
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
