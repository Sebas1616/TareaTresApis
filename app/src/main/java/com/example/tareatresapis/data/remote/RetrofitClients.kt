package com.example.tareatresapis.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClients {
    val rickApi: RickAndMortyApiService by lazy {
        Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RickAndMortyApiService::class.java)
    }

    val pokeApi: PokeApiService by lazy {
        Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PokeApiService::class.java)
    }

    val dogApi: DogApiService by lazy {
        Retrofit.Builder().baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(DogApiService::class.java)
    }

    val sqliteCloudApi: SqliteCloudApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://ctntquqjvk.g5.sqlite.cloud/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SqliteCloudApiService::class.java)
    }
}