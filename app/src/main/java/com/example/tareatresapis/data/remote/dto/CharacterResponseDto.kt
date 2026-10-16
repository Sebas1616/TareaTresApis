package com.example.tareatresapis.data.remote.dto

data class CharacterResponseDto(val results: List<CharacterDto>)
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)
