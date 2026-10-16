package com.example.tareatresapis.data.remote

import com.example.tareatresapis.data.remote.dto.PokemonDto
import retrofit2.Response
import retrofit2.http.GET

interface PokeApiService {
    @GET("pokemon/ditto")
    suspend fun getPokemonPrueba(): Response<PokemonDto>
}
