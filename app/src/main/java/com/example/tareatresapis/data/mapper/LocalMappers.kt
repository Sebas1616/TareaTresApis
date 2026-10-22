package com.example.tareatresapis.data.mapper

import com.example.tareatresapis.data.local.CharacterCacheEntity
import com.example.tareatresapis.data.local.FavoritoEntity
import com.example.tareatresapis.model.MergedCharacter

fun CharacterCacheEntity.toDomain(): MergedCharacter = MergedCharacter(
    id = id, name = name, status = status, species = species, image = image,
    pokemonNombre = null, pokemonExp = null, dogUrl = null
)

fun MergedCharacter.toCacheEntity(): CharacterCacheEntity = CharacterCacheEntity(
    id = id, name = name, status = status, species = species, image = image
)

fun FavoritoEntity.toDomain(): MergedCharacter = MergedCharacter(
    id = id, name = name, status = status, species = species, image = image,
    pokemonNombre = null, pokemonExp = null, dogUrl = null
)

fun MergedCharacter.toFavoritoEntity(): FavoritoEntity = FavoritoEntity(
    id = id, name = name, status = status, species = species, image = image
)
