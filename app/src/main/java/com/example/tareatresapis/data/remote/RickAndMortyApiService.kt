package com.example.tareatresapis.data.remote

import com.example.tareatresapis.data.remote.dto.CharacterResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponseDto>
}
