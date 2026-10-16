package com.example.tareatresapis.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val id: Int,
    val name: String,
    @SerializedName("base_experience") val baseExperience: Int
)
