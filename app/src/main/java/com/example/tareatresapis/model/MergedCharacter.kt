package com.example.tareatresapis.model

data class MergedCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val pokemonNombre: String? = null,
    val pokemonExp: Int? = null,
    val dogUrl: String? = null
)
