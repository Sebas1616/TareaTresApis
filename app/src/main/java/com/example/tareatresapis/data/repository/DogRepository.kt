package com.example.tareatresapis.data.repository

import com.example.tareatresapis.data.mapper.DogDomain
import com.example.tareatresapis.data.mapper.toDomain
import com.example.tareatresapis.data.remote.DogApiService

class DogRepository(private val api: DogApiService) {
    suspend fun fetchRandomDog(): DogDomain? {
        return try {
            val response = api.getRandomDog()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain()
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
