package com.example.tareatresapis.data.remote

import com.example.tareatresapis.data.remote.dto.DogDto
import retrofit2.Response
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): Response<DogDto>
}
