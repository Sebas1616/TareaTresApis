package com.example.tareatresapis.data.mapper

import com.example.tareatresapis.data.remote.dto.CharacterDto
import com.example.tareatresapis.data.remote.dto.DogDto
import com.example.tareatresapis.data.remote.dto.PokemonDto
import com.example.tareatresapis.model.MergedCharacter

data class PokemonDomain(val nombre: String, val experiencia: Int)
data class DogDomain(val imageUrl: String)

fun CharacterDto.toDomain(pokemonNombre: String?, pokemonExp: Int?, dogUrl: String?): MergedCharacter {
    return MergedCharacter(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        image = this.image,
        pokemonNombre = pokemonNombre,
        pokemonExp = pokemonExp,
        dogUrl = dogUrl
    )
}

fun PokemonDto.toDomain(): PokemonDomain {
    return PokemonDomain(nombre = this.name, experiencia = this.baseExperience)
}

fun DogDto.toDomain(): DogDomain {
    return DogDomain(imageUrl = this.message)
}
